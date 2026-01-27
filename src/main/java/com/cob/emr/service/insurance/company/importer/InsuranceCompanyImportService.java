package com.cob.emr.service.insurance.company.importer;

import org.springframework.web.multipart.MultipartFile;

public interface InsuranceCompanyImportService {
    int importFromExcel(MultipartFile file, Long organizationId) throws Exception;
}
