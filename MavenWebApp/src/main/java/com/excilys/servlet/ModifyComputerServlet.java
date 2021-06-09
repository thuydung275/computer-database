package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.controller.CompanyController;
import com.excilys.controller.ComputerController;
import com.excilys.dto.ComputerDTO;
import com.excilys.validator.CustomException;

@Controller
@RequestMapping("/computer/edit")
public class ModifyComputerServlet extends HttpServlet {

    private ComputerController computerController;
    private CompanyController companyController;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ModifyComputerServlet.class);

    public ModifyComputerServlet(ComputerController computerController, CompanyController companyController) {
        this.computerController = computerController;
        this.companyController = companyController;
    }

    @Override
    @GetMapping
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("companyList", companyController.getListCompanies());
        if (request.getParameter("id") != null) {
            String computerId = request.getParameter("id");
            ComputerDTO computer = computerController.findComputer(computerId);
            request.setAttribute("computer", computer);
        }
        request.getRequestDispatcher("/WEB-INF/modifyComputer.jsp").forward(request, response);
    }

    @Override
    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String computerName = request.getParameter("computerName");
        String introduced = request.getParameter("introduced");
        String discontinued = request.getParameter("discontinued");
        String companyId = request.getParameter("companyId");
        String computerId = request.getParameter("id");
        try {
            if (computerId != null) {
                computerController.updateComputer(computerId, computerName, introduced, discontinued, companyId);
                request.setAttribute("success", computerName + " was successfully updated !");
            } else {
                computerController.createComputer(computerName, introduced, discontinued, companyId);
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
