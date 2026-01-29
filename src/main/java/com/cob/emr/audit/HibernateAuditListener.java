package com.cob.emr.audit;

import com.cob.emr.entity.audit.AuditLog;
import com.cob.emr.repositories.audit.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
        persistAuditLog(event.getEntity(), "INSERT", event.getPersister(), event.getState(), null);
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        if (!isAudited(event.getEntity())) return;
        persistAuditLog(event.getEntity(), "UPDATE", event.getPersister(), event.getState(), event.getOldState());
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (!isAudited(event.getEntity())) return;
        persistAuditLog(event.getEntity(), "DELETE", event.getPersister(), event.getDeletedState(), null);
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    private boolean isAudited(Object entity) {
        return AUDITED_ENTITIES.contains(entity.getClass().getName());
    }

    private void persistAuditLog(Object entity, String action, EntityPersister persister,
                                 Object[] currentState, Object[] previousState) {
        try {
            AuditLog log = new AuditLog();
            log.setEntityName(entity.getClass().getSimpleName());
            log.setEntityId(extractEntityId(entity));
            log.setClinicId(extractClinicId(entity));
            log.setAction(action);
            log.setPerformedBy(extractPerformedBy());
            log.setPerformedAt(System.currentTimeMillis());

            String[] propertyNames = persister.getPropertyNames();
            if ("UPDATE".equals(action) && previousState != null) {
                log.setChanges(buildDiffJson(propertyNames, previousState, currentState));
            } else {
                log.setChanges(buildStateJson(propertyNames, currentState));
            }

            auditLogRepository.save(log);
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
        // For entity relations, extract the ID
        for (Field field : value.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return field.get(value);
                } catch (IllegalAccessException e) {
                    return value.toString();
                }
            }
        }
        return value.toString();
    }

    private Long extractEntityId(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    Object value = field.get(entity);
                    return value instanceof Long ? (Long) value : null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private Long extractClinicId(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if ("clinic".equals(field.getName()) && field.isAnnotationPresent(JoinColumn.class)) {
                field.setAccessible(true);
                try {
                    Object clinic = field.get(entity);
                    if (clinic == null) return null;
                    for (Field idField : clinic.getClass().getDeclaredFields()) {
                        if (idField.isAnnotationPresent(Id.class)) {
                            idField.setAccessible(true);
                            Object val = idField.get(clinic);
                            return val instanceof Long ? (Long) val : null;
                        }
                    }
                } catch (IllegalAccessException e) {
                    return null;
                }
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
