package com.cob.emr.service.insurance.company.importer;

import com.cob.emr.entity.insurance.InsuranceCompany;
import com.cob.emr.entity.organization.Organization;
import com.cob.emr.model.common.AddressDependencyModel;
import com.cob.emr.repositories.insurance.company.InsuranceCompanyRepository;
import com.cob.emr.repositories.organization.OrganizationRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class InsuranceCompanyImportServiceImpl implements InsuranceCompanyImportService {

    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public int importFromExcel(MultipartFile file, Long organizationId) throws Exception {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException("Organization not found with id: " + organizationId));

        List<InsuranceCompany> companies = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            if (headerRow == null)
                throw new IllegalArgumentException("Excel file is empty or missing header row");

            int colInsuranceCompany = -1;
            int colInsuranceCompanyType = -1;
            int colPhone = -1;
            int colFax = -1;
            int colAddressLine1 = -1;
            int colCountry = -1;
            int colCity = -1;
            int colZipCode = -1;

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null) continue;
                String header = cell.getStringCellValue().trim();
                switch (header) {
                    case "Insurance Company":
                        colInsuranceCompany = i;
                        break;
                    case "Insurance Company Type":
                        colInsuranceCompanyType = i;
                        break;
                    case "Phone":
                        colPhone = i;
                        break;
                    case "Fax":
                        colFax = i;
                        break;
                    case "Address Line 1":
                        colAddressLine1 = i;
                        break;
                    case "Country":
                        colCountry = i;
                        break;
                    case "City":
                        colCity = i;
                        break;
                    case "Zip Code":
                        colZipCode = i;
                        break;
                }
            }

            if (colInsuranceCompany == -1)
                throw new IllegalArgumentException("Missing required column: 'Insurance Company'");

            for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) continue;

                String name = getCellValueAsString(row.getCell(colInsuranceCompany));
                if (name == null || name.isEmpty()) continue;

                InsuranceCompany company = new InsuranceCompany();
                company.setName(name);
                company.setOrganization(organization);

                if (colInsuranceCompanyType != -1) {
                    String typeValue = getCellValueAsString(row.getCell(colInsuranceCompanyType));
                    if (typeValue != null && !typeValue.isEmpty()) {
                        company.setInsuranceType(typeValue);
                    }
                }
                if (colPhone != -1)
                    company.setPhone(getCellValueAsString(row.getCell(colPhone)));
                if (colFax != -1)
                    company.setFax(getCellValueAsString(row.getCell(colFax)));

                AddressDependencyModel address = new AddressDependencyModel();
                boolean hasAddress = false;

                if (colAddressLine1 != -1) {
                    String val = getCellValueAsString(row.getCell(colAddressLine1));
                    if (val != null && !val.isEmpty()) {
                        address.setFirstAddress(val);
                        hasAddress = true;
                    }
                }
                if (colCountry != -1) {
                    String val = getCellValueAsString(row.getCell(colCountry));
                    if (val != null && !val.isEmpty()) {
                        address.setCountry(val);
                        hasAddress = true;
                    }
                }
                if (colCity != -1) {
                    String val = getCellValueAsString(row.getCell(colCity));
                    if (val != null && !val.isEmpty()) {
                        address.setCity(val);
                        hasAddress = true;
                    }
                }
                if (colZipCode != -1) {
                    String val = getCellValueAsString(row.getCell(colZipCode));
                    if (val != null && !val.isEmpty()) {
                        address.setZipCode(val);
                        hasAddress = true;
                    }
                }

                if (hasAddress)
                    company.setAddresses(Collections.singletonList(address));

                companies.add(company);
            }
        }

        insuranceCompanyRepository.saveAll(companies);
        return companies.size();
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

}
