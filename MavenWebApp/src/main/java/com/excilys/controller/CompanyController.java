package com.excilys.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;

@Component
public class CompanyController {

    private CompanyService companyService;
    private CompanyMapper companyMapper;
    // TODO: make companyMapper inner member class

    public CompanyController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    public List<CompanyDTO> getListCompanies() {
        List<Company> companyList = companyService.getList();
        return companyList.stream().map(c -> companyMapper.mapFromCompanyToDTO(c)).collect(Collectors.toList());
    }

    public void deleteCompany(String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            companyService.delete(Integer.parseInt(idCompany));
        }

    }

}
