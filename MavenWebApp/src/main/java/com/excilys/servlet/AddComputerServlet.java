package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.CustomException;

@WebServlet(name = "AddComputerServlet", urlPatterns = { "/computer/add" })
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AddComputerServlet.class);
    private static ComputerService computerService = new ComputerService();
    private static CompanyService companyService = new CompanyService();
    private static ComputerValidator computerValidator = ComputerValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Company> companyList = companyService.getListCompanies();
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

        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder().withName(computerName).withIntroduced(introduced)
                .withDiscontinued(discontinued).withCompanyId(companyId).build();
        try {
            computerValidator.validateComputerDTO(computerDTO);
            Computer computer = ComputerMapper.setObject(computerDTO);
            computerService.create(computer);
            request.setAttribute("success", computerName + " was successfully added !");
            doGet(request, response);
        } catch (CustomException ex) {
            log.error(ex.getMessage());
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }
}
