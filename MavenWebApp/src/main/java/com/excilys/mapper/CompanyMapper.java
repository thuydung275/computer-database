package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;

@Component
public class CompanyMapper {
    /**
     *
     * @param result
     * @return Company
     * @throws SQLException
     */
    public Company mapFromResultSetToCompany(ResultSet result) throws SQLException {
        CompanyBuilder builder = new Company.CompanyBuilder();

        builder.withId(result.getInt("company.id"));

        if (result.getString("company.name") != null) {
            builder.withName(result.getString("company.name"));
        }

        Company company = builder.build();
        return company;
    }

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
