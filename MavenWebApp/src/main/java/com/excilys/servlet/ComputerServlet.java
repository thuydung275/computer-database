package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.service.Pagination;

@WebServlet("/computer")
public class ComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // private static CompanyService companyService = new CompanyService();
    // private static Logger log = Logger.getLogger(ComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Pagination page = new Pagination(0);
//        this.getServletContext().getRequestDispatcher("/WEB-INF/test.jsp").forward(request, response);
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
