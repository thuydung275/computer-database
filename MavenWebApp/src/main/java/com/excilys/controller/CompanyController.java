package com.excilys.controller;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;

public class CompanyController {

    public static List<Company> getListCompanies() {
        return new CompanyService().getListCompanies();
    }

}
