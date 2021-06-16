package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.model.Company;
import com.excilys.repository.CompanyRepository;

/**
 *
 * @author thuydung
 *
 */
@Service
public class CompanyService {

    private CompanyRepository companyRepository;
    private static Logger log = Logger.getLogger(CompanyService.class);

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company findById(int id) {
        Optional<Company> opt = companyRepository.findById(id);
        return opt.map(c -> c).orElse(null);
    }

    public List<Company> getList() {
        return companyRepository.findAll();
    }

    public boolean delete(Integer idCompany) {
        return companyRepository.delete(idCompany);
    }
}
