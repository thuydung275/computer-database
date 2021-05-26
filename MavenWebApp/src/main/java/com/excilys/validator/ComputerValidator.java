package com.excilys.validator;

import java.time.LocalDate;

import com.excilys.dto.ComputerDTO;

public class ComputerValidator {

    private ComputerValidator() {
    }

    private static class SingletonHolder {
        private static final ComputerValidator INSTANCE = new ComputerValidator();
    }

    public static ComputerValidator getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void validateComputerDTO(ComputerDTO computerDTO) throws CustomException {
        validateName(computerDTO.getName());
        validateDate(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
    }

    private void validateName(String name) {
        if (name == null || "".equals(name) || name.isEmpty()) {
            throw new CustomException("Name must not be empty !");
        }
    }

    private void validateDate(String introduced, String discontinued) {
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
                throw new CustomException("discontinued date is smaller then introduced date",
                        CustomException.ER_INTRODUCED_BIGGER_DISCONTINUED);
            }
        } else if (introducedDate == null && discontinuedDate != null) {
            throw new CustomException("introduced date is null while discontinued date is not null",
                    CustomException.ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL);
        }

    }

}
