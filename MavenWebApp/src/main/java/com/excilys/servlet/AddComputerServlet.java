package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.controller.CompanyController;
import com.excilys.controller.ComputerController;
import com.excilys.model.Company;
import com.excilys.validator.CustomException;

@WebServlet(name = "AddComputerServlet", urlPatterns = { "/computer/add" })
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AddComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Company> companyList = CompanyController.getListCompanies();
        request.setAttribute("companyList", companyList);
        this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String computerName = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyId");

        try {
            ComputerController.createComputer(computerName, introduced, discontinued, companyId);
            request.setAttribute("success", computerName + " was successfully added !");
            doGet(request, response);
        } catch (CustomException ex) {
            log.error(ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
