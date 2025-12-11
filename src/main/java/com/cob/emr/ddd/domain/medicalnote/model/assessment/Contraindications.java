package com.cob.emr.ddd.domain.medicalnote.model.assessment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Contraindications {
    private final boolean hasContraindications;
    private final String details;
    
    public boolean hasContraindication(String keyword) {
        return hasContraindications && 
               details != null && 
               details.toLowerCase().contains(keyword.toLowerCase());
    }
}
