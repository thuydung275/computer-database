package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;
import com.excilys.model.Computer;
import com.excilys.model.Computer.ComputerBuilder;

@Component
public class ComputerMapper {
    /**
     *
     * @param result
     * @return Computer
     * @throws SQLException
     */
    public Computer mapFromResultSetToComputer(ResultSet result) throws SQLException {
        ComputerBuilder builder = new Computer.ComputerBuilder();

        builder.withId(result.getInt("computer.id"));

        if (result.getString("computer.name") != null) {
            builder.withName(result.getString("computer.name"));
        }

        if (result.getDate("computer.introduced") != null) {
            builder.withIntroduced(result.getDate("computer.introduced").toLocalDate());
        }

        if (result.getDate("computer.discontinued") != null) {
            builder.withDiscontinued(result.getDate("computer.discontinued").toLocalDate());
        }

        // hydrate Company
        if (result.getInt("computer.company_id") != 0) {
            CompanyBuilder companyBuilder = new Company.CompanyBuilder().withId(result.getInt("computer.company_id"));
            if (result.getString("company.name") != null) {
                companyBuilder.withName(result.getString("company.name"));
            }
            Company company = companyBuilder.build();
            builder.withCompany(company);
        }

        Computer computer = builder.build();
        return computer;
    }

    public Computer mapFromDTOtoComputer(ComputerDTO computerDTO) {
        Computer computer = new Computer.ComputerBuilder().build();

        if (computerDTO.getId() != null && !computerDTO.getId().isEmpty()) {
            computer.setId(Integer.parseInt(computerDTO.getId()));
        }
        if (computerDTO.getName() != null && !computerDTO.getName().isEmpty()) {
            computer.setName(computerDTO.getName());
        }
        if (computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().isEmpty()) {
            computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
        }
        if (computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().isEmpty()) {
            computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
        }
        if (computerDTO.getCompanyId() != null && !computerDTO.getCompanyId().isEmpty()) {
            Company company = new Company.CompanyBuilder().withId(Integer.parseInt(computerDTO.getCompanyId())).build();
            computer.setCompany(company);
        }

        return computer;
    }

    public ComputerDTO mapFromComputerToDTO(Computer computer) {
        ComputerDTO comDTO = new ComputerDTO.ComputerDTOBuilder().build();
        if (computer.getId() != null) {
            comDTO.setId(computer.getId().toString());
        }
        if (computer.getName() != null && !computer.getName().isEmpty()) {
            comDTO.setName(computer.getName());
        }
        if (computer.getIntroduced() != null) {
            comDTO.setIntroduced(computer.getIntroduced().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (computer.getDiscontinued() != null) {
            comDTO.setDiscontinued(computer.getDiscontinued().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (computer.getCompany() != null) {
            comDTO.setCompanyId(computer.getCompany().getId().toString());
            comDTO.setCompanyName(computer.getCompany().getName());
        }
        return comDTO;
    }
}
