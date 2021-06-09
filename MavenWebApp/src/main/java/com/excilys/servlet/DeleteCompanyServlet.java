package com.excilys.servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.service.CompanyService;

@Controller
public class DeleteCompanyServlet {

    private CompanyService companyService;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(DeleteCompanyServlet.class);

    public DeleteCompanyServlet(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company/delete")
    protected RedirectView deleteCompany(@RequestParam(value = "id", required = false) String idCompany) {
        if (idCompany != null && StringUtils.isNumeric(idCompany)) {
            companyService.delete(Integer.parseInt(idCompany));
        }
        return new RedirectView("/computer/list", true);
    }
}
