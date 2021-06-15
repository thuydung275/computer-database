package com.excilys.service;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.aop.proxy.TimedInvocationHandler;
import com.excilys.model.Company;
import com.excilys.repository.CompanyRepository;
import com.excilys.repository.CompanyRepositoryInterface;

/**
 *
 * @author thuydung
 *
 */
@Service
public class CompanyService {

    private CompanyRepositoryInterface companyRepositoryInterface;
    private static Logger log = Logger.getLogger(CompanyService.class);

    public CompanyService(CompanyRepository companyRepository) {
        CompanyRepositoryInterface companyRepositoryInterface = (CompanyRepositoryInterface) Proxy.newProxyInstance(
                CompanyRepositoryInterface.class.getClassLoader(), new Class[] { CompanyRepositoryInterface.class },
                new TimedInvocationHandler(companyRepository));
        this.companyRepositoryInterface = companyRepositoryInterface;
    }

    public Company findById(int id) {
        Optional<Company> opt = companyRepositoryInterface.findById(id);
        return opt.map(c -> c).orElse(null);
    }

    public List<Company> getList() {
        return companyRepositoryInterface.findAll();
    }

    public boolean delete(int idCompany) {
        return companyRepositoryInterface.delete(idCompany);
    }
}
