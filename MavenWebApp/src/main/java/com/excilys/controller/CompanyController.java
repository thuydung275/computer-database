package com.excilys.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;

public class CompanyController {

    public static List<Company> getListCompanies() {
        return new CompanyService().getListCompanies();
    }

    public static void deleteCompany(String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            new CompanyService().deleteCompany(Integer.parseInt(idCompany));
        }

    }

}
