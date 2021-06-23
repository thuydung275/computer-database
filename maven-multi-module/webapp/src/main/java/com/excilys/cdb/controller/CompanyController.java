package com.excilys.cdb.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;
    private static Logger log = Logger.getLogger(CompanyController.class);

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @DeleteMapping(value = "/delete/{id}")
    public @ResponseBody ResponseEntity<String> deleteCompany(@PathVariable("id") Integer id) {
        String result = Boolean.valueOf(companyService.delete(id)).toString();
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
