package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;

/**
 *
 * @author thuydung
 *
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyDAO companyInstance;
    private static Logger log = Logger.getLogger(CompanyService.class);

    public void setCompanyInstance(CompanyDAO companyInstance) {
        this.companyInstance = companyInstance;
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
        return null;
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
        return null;
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

    public boolean deleteCompany(int idCompany) {
        return companyInstance.delete(idCompany);
    }

}
