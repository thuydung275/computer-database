package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.controller.CompanyController;

@WebServlet(name = "DeleteCompanyServlet", urlPatterns = { "/company/delete" })
public class DeleteCompanyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(DeleteCompanyServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idCompany = request.getParameter("id");
        CompanyController.deleteCompany(idCompany);
        response.sendRedirect(request.getContextPath() + "/computer/list");
    }
}
