package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;

@Component
public class ComputerMapper implements RowMapper<Computer> {
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

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Computer computer = new Computer.ComputerBuilder().build();
        computer.setId(rs.getInt("computer.id"));
        computer.setName(rs.getString("computer.name"));
        if (rs.getDate("computer.introduced") != null) {
            computer.setIntroduced(rs.getDate("computer.introduced").toLocalDate());
        }
        if (rs.getDate("computer.discontinued") != null) {
            computer.setDiscontinued(rs.getDate("computer.discontinued").toLocalDate());
        }
        if (rs.getInt("computer.company_id") != 0) {
            Company company = new Company.CompanyBuilder().withId(rs.getInt("computer.company_id"))
                    .withName(rs.getString("company.name")).build();
            computer.setCompany(company);
        }
        return computer;
    }
}
