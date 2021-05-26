package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.Pagination;

@WebServlet(name = "ListComputerServlet", urlPatterns = { "/computer/list", "/listComputer", "/dashboard" })
public class ListComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ComputerService computerService = new ComputerService();
    private static Pagination page;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        page = new Pagination(0);
        page.setTotalItem(computerService.getListComputers().size());
        String pageNumber = request.getParameter("page");
        String perPage = request.getParameter("perPage");
        if (pageNumber != null && StringUtils.isNumeric(pageNumber)) {
            page.setPage(Integer.parseInt(pageNumber));
        }
        if (perPage != null && StringUtils.isNumeric(perPage)) {
            page.setLimit(Integer.parseInt(perPage));
        }
        List<Computer> computerList = computerService.getListPerPage(page);
        request.setAttribute("computerList", computerList);
        request.setAttribute("pagination", page);
        this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }

}
