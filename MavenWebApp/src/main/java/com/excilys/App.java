package com.excilys;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class App {
    private static Logger log = Logger.getLogger(App.class);
    private static CompanyService companyService = new CompanyService();
    private static ComputerService computerService = new ComputerService();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Company company = companyService.findById(1);
        Company newCompany = companyService.findByName(company.getName());
        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("name", "CM");
        criteria.put("order", "ASC");
        criteria.put("sort", "computer.name");
        criteria.put("limit", "0,5");
//        List<Computer> computerList = computerService.findByCriteria(criteria);
//        log.debug("==================computerList=====================");
//        log.debug(computerList.size());
        // log.debug(newCompany.toString());
        // log.debug(computerService.getListComputers().size());
    }

}
