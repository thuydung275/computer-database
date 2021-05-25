package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.service.CompanyService;
import com.excilys.service.Pagination;

@WebServlet("/company")
public class CompanyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static CompanyService companyService = new CompanyService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalCompany = companyService.getListCompanies().size();

        Pagination page = new Pagination(totalCompany);
        this.getServletContext().getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("index.jsp").forward(request, response);
    }

}
