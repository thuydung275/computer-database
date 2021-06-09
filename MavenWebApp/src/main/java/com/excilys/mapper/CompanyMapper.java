package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {
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

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company.CompanyBuilder().build();
        company.setId(rs.getInt("company.id"));
        company.setName(rs.getString("company.name"));
        return company;
    }
}
