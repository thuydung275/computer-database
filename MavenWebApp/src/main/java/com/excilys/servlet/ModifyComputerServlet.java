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
import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.validator.CustomException;

@WebServlet(name = "ModifyComputerServlet", urlPatterns = { "/computer/add", "/computer/edit" })
public class ModifyComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ModifyComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Company> companyList = CompanyController.getListCompanies();
        request.setAttribute("companyList", companyList);
        if (request.getParameter("id") != null) {
            String computerId = request.getParameter("id");
            ComputerDTO computer = ComputerController.findComputer(computerId);
            request.setAttribute("computer", computer);
            this.getServletContext().getRequestDispatcher("/views/modifyComputer.jsp").forward(request, response);
        } else {
            this.getServletContext().getRequestDispatcher("/views/modifyComputer.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String computerName = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyId");
        String computerId = request.getParameter("id");

        try {
            if (computerId != null) {
                ComputerController.updateComputer(computerId, computerName, introduced, discontinued, companyId);
                request.setAttribute("success", computerName + " was successfully updated !");
            } else {
                ComputerController.createComputer(computerName, introduced, discontinued, companyId);
                request.setAttribute("success", computerName + " was successfully added !");
            }
            doGet(request, response);
        } catch (CustomException ex) {
            log.error(ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
