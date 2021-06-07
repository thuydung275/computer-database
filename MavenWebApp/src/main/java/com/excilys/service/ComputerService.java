package com.excilys.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.validator.CustomException;

/**
 *
 * @author thuydung
 *
 */
@Service
public class ComputerService {

    @Autowired
    private ComputerDAO computerInstance;
    @Autowired
    private CompanyService companyService;
    private static Logger log = Logger.getLogger(ComputerService.class);

    /**
     *
     * @param computerInstance
     */
    public void setComputerInstance(ComputerDAO computerInstance) {
        this.computerInstance = computerInstance;
    }

    /**
     *
     * @param companyInstance
     */
    public void setCompanyInstance(CompanyDAO companyInstance) {
        this.companyService.setCompanyInstance(companyInstance);
    }

    /**
     *
     * @return List<Computer>
     */
    public List<Computer> getListComputers() {
        return computerInstance.getList();
    }

    /**
     *
     * @return List<Computer>
     */
    public List<Computer> findByCriteria(Map<String, String> criteria) {
        return computerInstance.findByCriteria(criteria);
    }

    /**
     *
     * @param page
     * @return List<Computer>
     */
    public List<Computer> getListPerPage(Pagination page) {
        return computerInstance.getListPerPage(page);
    }

    /**
     *
     * @param id
     * @return Computer
     */
    public Computer findById(int id) {
        Optional<Computer> opt = computerInstance.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer create(Computer computer) {
        validateDate(computer);
        if (computer.getCompany() != null) {
            if (computer.getCompany().getId() != 0) {
                Company company = companyService.findById(computer.getCompany().getId());
                if (company == null) {
                    throw new CustomException("company does not exist in our database", CustomException.ER_NOT_FOUND);
                }
            } else {
                throw new CustomException("company does not exist in our database", CustomException.ER_NOT_FOUND);
            }
        }
        return computerInstance.create(computer);
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer update(Computer computer) {
        Optional<Computer> computerFromDB = computerInstance.findById(computer.getId());
        if (!computerFromDB.isPresent()) {
            throw new CustomException("Computer does not exist in our database", CustomException.ER_NOT_FOUND);
        }

        validateDate(computer);
        return computerInstance.update(computer);

    }

    private void validateDate(Computer computer) {
        if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
            if (computer.getDiscontinued().compareTo(computer.getIntroduced()) < 0) {
                throw new CustomException("discontinued date is smaller then introduced date",
                        CustomException.ER_INTRODUCED_BIGGER_DISCONTINUED);
            }
        } else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
            throw new CustomException("introduced date is null while discontinued date is not null",
                    CustomException.ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL);
        }
        return;
    }

    /**
     *
     * @param computer
     * @return boolean
     */
    public boolean remove(Computer computer) {
        Optional<Computer> computerFromDB = computerInstance.findById(computer.getId());
        if (!computerFromDB.isPresent()) {
            throw new CustomException("Computer does not exist in our database", CustomException.ER_NOT_FOUND);
        }
        return computerInstance.delete(computer.getId());
    }
}
