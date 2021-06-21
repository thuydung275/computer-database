package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerMapper {
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