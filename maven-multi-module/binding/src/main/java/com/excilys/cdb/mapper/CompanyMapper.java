package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {
    public Company mapFromDTOToCompany(CompanyDTO companyDTO) {
        Company company = new Company.CompanyBuilder().build();
        if (companyDTO.getId() != null && !companyDTO.getId().isEmpty()) {
            company.setId(Integer.parseInt(companyDTO.getId()));
        }
        if (companyDTO.getName() != null && !companyDTO.getName().isEmpty()) {
            company.setName(companyDTO.getName());
        }
        return company;
    }

    public CompanyDTO mapFromCompanyToDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO.CompanyDTOBuilder().build();
        if (company.getId() != null) {
            companyDTO.setId(company.getId().toString());
        }
        if (company.getName() != null) {
            companyDTO.setName(company.getName());
        }
        return companyDTO;
    }
}
