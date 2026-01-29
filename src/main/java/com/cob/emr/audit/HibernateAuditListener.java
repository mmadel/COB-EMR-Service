package com.cob.emr.audit;

import com.cob.emr.entity.audit.AuditLog;
import com.cob.emr.repositories.audit.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class HibernateAuditListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    private static final Logger log = LoggerFactory.getLogger(HibernateAuditListener.class);

    private static final Set<String> AUDITED_ENTITIES = Set.of(
            "com.cob.emr.entity.appointment.Appointment",
            "com.cob.emr.entity.calendar.CalendarClinicEntity"
    );

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public HibernateAuditListener(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if (!isAudited(event.getEntity())) return;
        persistAuditLog(event.getEntity(), "INSERT", event.getPersister(), event.getState(), null, event.getSession());
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (!isAudited(event.getEntity())) return;
        persistAuditLog(event.getEntity(), "UPDATE", event.getPersister(), event.getState(), event.getOldState(), event.getSession());
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (!isAudited(event.getEntity())) return;
        persistAuditLog(event.getEntity(), "DELETE", event.getPersister(), event.getDeletedState(), null, event.getSession());
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    private boolean isAudited(Object entity) {
        return AUDITED_ENTITIES.contains(entity.getClass().getName());
    }

    private void persistAuditLog(Object entity, String action, EntityPersister persister,
                                 Object[] currentState, Object[] previousState,
                                 SessionImplementor session) {
        try {
            String[] propertyNames = persister.getPropertyNames();

            AuditLog auditLog = new AuditLog();
            auditLog.setEntityName(entity.getClass().getSimpleName());
            auditLog.setEntityId(extractEntityId(entity));
            auditLog.setClinicId(extractIdFromState(propertyNames, currentState, "clinic", session));
            auditLog.setCalendarId(extractIdFromState(propertyNames, currentState, "calendar", session));
            auditLog.setPatientId(extractIdFromState(propertyNames, currentState, "patient", session));
            auditLog.setAction(action);
            auditLog.setPerformedBy(extractPerformedBy());
            auditLog.setPerformedAt(System.currentTimeMillis());

            if ("UPDATE".equals(action) && previousState != null) {
                auditLog.setChanges(buildDiffJson(propertyNames, previousState, currentState));
            } else {
                auditLog.setChanges(buildStateJson(propertyNames, currentState));
            }

            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.error("Audit failed for {} id={}: {}", entity.getClass().getSimpleName(), extractEntityId(entity), e.getMessage(), e);
        }
    }

    private String buildDiffJson(String[] propertyNames, Object[] oldState, Object[] newState) {
        try {
            Map<String, Object[]> diff = new LinkedHashMap<>();
            for (int i = 0; i < propertyNames.length; i++) {
                Object oldVal = resolveValue(oldState[i]);
                Object newVal = resolveValue(newState[i]);
                if (!Objects.equals(oldVal, newVal)) {
                    diff.put(propertyNames[i], new Object[]{oldVal, newVal});
                }
            }
            return objectMapper.writeValueAsString(diff);
        } catch (Exception e) {
            return "{}";
        }
    }

    private String buildStateJson(String[] propertyNames, Object[] state) {
        try {
            Map<String, Object> snapshot = new LinkedHashMap<>();
            for (int i = 0; i < propertyNames.length; i++) {
                snapshot.put(propertyNames[i], resolveValue(state[i]));
            }
            return objectMapper.writeValueAsString(snapshot);
        } catch (Exception e) {
            return "{}";
        }
    }

    private Object resolveValue(Object value) {
        if (value == null) return null;
        if (value instanceof Enum) return ((Enum<?>) value).name();
        if (value instanceof String || value instanceof Number || value instanceof Boolean) return value;
        // For entity relations (including Hibernate proxies), extract the ID
        // Use Hibernate.getClass() to get the real entity class, not the proxy subclass
        Class<?> entityClass = Hibernate.getClass(value);
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    // Unproxy to access fields on the real entity instance
                    Object unproxied = Hibernate.unproxy(value);
                    return field.get(unproxied);
                } catch (IllegalAccessException e) {
                    return value.toString();
                }
            }
        }
        return value.toString();
    }

    private Long extractEntityId(Object entity) {
        Class<?> entityClass = Hibernate.getClass(entity);
        Object unproxied = Hibernate.unproxy(entity);
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(unproxied);
                    return value instanceof Long ? (Long) value : null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private Long extractIdFromState(String[] propertyNames, Object[] state,
                                    String propertyName, SessionImplementor session) {
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyName.equals(propertyNames[i])) {
                Object value = state[i];
                if (value == null) return null;
                try {
                    // Use Hibernate.getClass() to get the real entity class, not the proxy subclass
                    String entityName = Hibernate.getClass(value).getName();
                    Serializable id = session.getEntityPersister(entityName, value)
                            .getIdentifier(value, session);
                    if (id instanceof Long) return (Long) id;
                    if (id instanceof Number) return ((Number) id).longValue();
                } catch (Exception e) {
                    log.warn("Could not extract {} id: {}", propertyName, e.getMessage());
                }
                return null;
            }
        }
        return null;
    }

    private String extractPerformedBy() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) auth.getPrincipal();
                return jwt.getSubject();
            }
        } catch (Exception e) {
            // SecurityContext not available
        }
        return "SYSTEM";
    }
}
