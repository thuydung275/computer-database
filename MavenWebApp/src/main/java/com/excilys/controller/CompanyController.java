package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;

@Component
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper companyMapper;

    public List<CompanyDTO> getListCompanies() {
        List<Company> companyList = companyService.getListCompanies();
        return companyList.stream().map(c -> companyMapper.mapFromCompanyToDTO(c)).collect(Collectors.toList());
    }

    public void deleteCompany(String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            companyService.deleteCompany(Integer.parseInt(idCompany));
        }

    }

}
