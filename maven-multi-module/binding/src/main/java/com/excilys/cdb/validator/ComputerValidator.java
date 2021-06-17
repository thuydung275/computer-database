package com.excilys.cdb.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;

@Component
public class ComputerValidator {

    public void validateComputerDTO(ComputerDTO computerDTO) throws Exception {
        validateName(computerDTO.getName());
        validateDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
    }

    private void validateName(String name) throws Exception {
        if (name == null || "".equals(name) || name.isEmpty()) {
            throw new Exception("Name must not be empty !");
        }
    }

    private void validateDate(String introduced, String discontinued) throws Exception {
        LocalDate introducedDate = null;
        LocalDate discontinuedDate = null;

        if (introduced != null && !introduced.isEmpty()) {
            introducedDate = LocalDate.parse(introduced);
        }

        if (discontinued != null && !discontinued.isEmpty()) {
            discontinuedDate = LocalDate.parse(discontinued);
        }

        if (introducedDate != null && discontinuedDate != null) {
            if (discontinuedDate.isBefore(introducedDate)) {
                throw new Exception("discontinued date is smaller then introduced date");
            }
        } else if (introducedDate == null && discontinuedDate != null) {
            throw new Exception("introduced date is null while discontinued date is not null");
        }

    }

}
