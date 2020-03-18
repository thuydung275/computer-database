package com.excilys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;

public class App {
	
	private static Logger log = LoggerFactory.getLogger(App.class);
	private static CompanyService companyService = new CompanyService();

	public static void main(String[] args) {
		
		Company company = companyService.findById(1);
		Company newCompany = companyService.findByName(company.getName());
		log.debug(newCompany.toString());
	}

}
