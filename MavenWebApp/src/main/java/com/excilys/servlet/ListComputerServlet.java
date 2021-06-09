package com.excilys.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.controller.ComputerController;
import com.excilys.dto.ComputerDTO;
import com.excilys.service.Pagination;

@Controller
@RequestMapping("/computer")
public class ListComputerServlet extends HttpServlet {

    private ComputerController computerController;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ListComputerServlet.class);

    public ListComputerServlet(ComputerController computerController) {
        this.computerController = computerController;
    }

    @Override
    @GetMapping("/list")
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
        Pagination page = computerController.getPage(criteria);
        List<ComputerDTO> computerList = computerController.getComputerListPerPage(criteria);
        List<Integer> maxTotalPage = computerController.getMaxPagePerLimit(criteria);
        request.setAttribute("computerList", computerList);
        request.setAttribute("pagination", page);
        request.setAttribute("maxTotalPage", maxTotalPage);
        request.setAttribute("search", search);
        request.setAttribute("order", order);
        request.setAttribute("sort", sort);

        String url = computerController.setUrl(search, order, sort);
        if (url.length() > 1) {
            request.setAttribute("url", url + "&");
        }
        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }
}
