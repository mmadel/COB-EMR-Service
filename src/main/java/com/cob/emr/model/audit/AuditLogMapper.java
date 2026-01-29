package com.cob.emr.model.audit;

import com.cob.emr.entity.audit.AuditLog;
import com.cob.emr.model.user.EMRUser;
import com.cob.emr.usecases.user.FindUserByUUIDUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuditLogMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final FindUserByUUIDUseCase findUserByUUIDUseCase;
    private final AuditFieldResolver fieldResolver;

    public AuditLogMapper(FindUserByUUIDUseCase findUserByUUIDUseCase,
                          AuditFieldResolver fieldResolver) {
        this.findUserByUUIDUseCase = findUserByUUIDUseCase;
        this.fieldResolver = fieldResolver;
    }

    public Page<AuditLogModel> toModelPage(Page<AuditLog> page) {
        Map<String, String> userNameCache = new HashMap<>();
        AuditFieldResolver.ResolverCache resolverCache = fieldResolver.createCache();
        return page.map(entity -> toModel(entity, userNameCache, resolverCache));
    }

    private AuditLogModel toModel(AuditLog entity, Map<String, String> userNameCache,
                                  AuditFieldResolver.ResolverCache resolverCache) {
        AuditLogModel model = new AuditLogModel();
        model.setId(entity.getId());
        model.setClinicId(entity.getClinicId());
        model.setEntityName(entity.getEntityName());
        model.setEntityId(entity.getEntityId());
        model.setAction(entity.getAction());
        model.setPerformedByUuid(entity.getPerformedBy());
        model.setPerformedByName(resolveUserName(entity.getPerformedBy(), userNameCache));
        model.setPerformedAt(entity.getPerformedAt());
        model.setChanges(parseChanges(entity.getChanges(), entity.getAction(), resolverCache));
        return model;
    }

    private String resolveUserName(String uuid, Map<String, String> cache) {
        if (uuid == null || "SYSTEM".equals(uuid)) return uuid;
        return cache.computeIfAbsent(uuid, this::lookupUserName);
    }

    private String lookupUserName(String uuid) {
        try {
            EMRUser user = findUserByUUIDUseCase.find(uuid);
            String name = buildFullName(user.getFirstName(), user.getMiddleName(), user.getLastName());
            return name != null ? name : uuid;
        } catch (Exception e) {
            return uuid;
        }
    }

    private List<AuditFieldChange> parseChanges(String json, String action,
                                                AuditFieldResolver.ResolverCache cache) {
        List<AuditFieldChange> result = new ArrayList<>();
        if (json == null || json.isBlank()) return result;
        try {
            Map<String, Object> raw = objectMapper.readValue(json, new TypeReference<>() {});
            for (Map.Entry<String, Object> entry : raw.entrySet()) {
                String field = entry.getKey();
                Object value = entry.getValue();
                if ("UPDATE".equals(action) && value instanceof List) {
                    List<?> pair = (List<?>) value;
                    Object oldVal = pair.size() > 0 ? pair.get(0) : null;
                    Object newVal = pair.size() > 1 ? pair.get(1) : null;
                    if (fieldResolver.isResolvable(field)) {
                        oldVal = fieldResolver.resolve(field, oldVal, cache);
                        newVal = fieldResolver.resolve(field, newVal, cache);
                    }
                    result.add(new AuditFieldChange(field, oldVal, newVal));
                } else {
                    if (fieldResolver.isResolvable(field)) {
                        value = fieldResolver.resolve(field, value, cache);
                    }
                    result.add(new AuditFieldChange(field, null, value));
                }
            }
        } catch (JsonProcessingException e) {
            result.add(new AuditFieldChange("raw", null, json));
        }
        return result;
    }

    private String buildFullName(String first, String middle, String last) {
        StringBuilder sb = new StringBuilder();
        if (first != null && !first.isBlank()) sb.append(first);
        if (middle != null && !middle.isBlank()) sb.append(" ").append(middle);
        if (last != null && !last.isBlank()) sb.append(" ").append(last);
        return sb.length() > 0 ? sb.toString().trim() : null;
    }
}
