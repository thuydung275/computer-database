package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.controller.CompanyController;

@Controller
@RequestMapping("/company/delete")
public class DeleteCompanyServlet extends HttpServlet {

    @Autowired
    private CompanyController companyController;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(DeleteCompanyServlet.class);

    @Override
    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCompany = request.getParameter("id");
        companyController.deleteCompany(idCompany);
        response.sendRedirect(request.getContextPath() + "/computer/list");
    }
}
