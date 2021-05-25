package com.excilys.service;

import java.util.List;
import java.util.Optional;

import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;
import com.excilys.validator.CustomException;

/**
 *
 * @author thuydung
 *
 */
public class CompanyService {

    private static CompanyDAO companyInstance = CompanyDAO.getInstance();

    public void setCompanyInstance(CompanyDAO companyInstance) {
        CompanyService.companyInstance = companyInstance;
    }

    /**
     *
     * @param name
     * @return Company
     */
    public Company findByName(String name) {
        Optional<Company> opt = companyInstance.findByName(name);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CustomException(name + CustomException.TEXT_ER_NOT_FOUND, CustomException.ER_NOT_FOUND);
    }

    /**
     *
     * @param id
     * @return Company
     */
    public Company findById(int id) {
        Optional<Company> opt = companyInstance.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new CustomException(id + CustomException.TEXT_ER_NOT_FOUND, CustomException.ER_NOT_FOUND);
    }

    /**
     *
     * @return List<Company>
     */
    public List<Company> getListCompanies() {
        return companyInstance.getList();
    }

    /**
     *
     * @return number of total companies
     */
    public int getCompanyNumber() {
        return companyInstance.getList().size();
    }

    /**
     *
     * @param page
     * @return List<Company>
     */
    public List<Company> getListPerPage(Pagination page) {
        return companyInstance.getListPerPage(page);
    }

}
