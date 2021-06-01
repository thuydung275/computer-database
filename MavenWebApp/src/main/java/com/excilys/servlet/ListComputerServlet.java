package com.excilys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.excilys.controller.ComputerController;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.Pagination;

@WebServlet(name = "ListComputerServlet", urlPatterns = { "/computer/list" })
public class ListComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ListComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageNumber = request.getParameter("page");
        String perPage = request.getParameter("perPage");
        String search = request.getParameter("search");
        if (StringUtils.isNotBlank(search)) {
            search.trim();
        }
        String order = request.getParameter("order");
        String sort = request.getParameter("sort");

        Map<String, String> criteria = new HashMap<String, String>();
        criteria.put("pageNumber", pageNumber);
        criteria.put("perPage", perPage);
        criteria.put("name", search);
        criteria.put("order", order);
        criteria.put("sort", sort);

        Pagination page = ComputerController.getPage(criteria);
        List<ComputerDTO> computerList = ComputerController.getComputerListPerPage(criteria);
        List<Integer> maxTotalPage = ComputerController.getMaxPagePerLimit(criteria);
        request.setAttribute("computerList", computerList);
        request.setAttribute("pagination", page);
        request.setAttribute("maxTotalPage", maxTotalPage);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("sort", sort);

        String url = ComputerController.setUrl(search, order, sort);
        if (url.length() > 1) {
            request.setAttribute("url", url + "&");
        }
        this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }
}
