package com.excilys.cdb.servlet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.Pagination;

@Controller
public class ListComputerServlet {

    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ListComputerServlet.class);

    public ListComputerServlet(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping("/computer/list")
    protected ModelAndView getComputerList(@RequestParam(value = "page", required = false) String pageNumber,
            @RequestParam(value = "perPage", required = false) String perPage,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "sort", required = false) String sort) {
        Map<String, String> criteria = new HashMap<>();
        criteria.put("pageNumber", pageNumber);
        criteria.put("perPage", perPage);
        criteria.put("order", order);
        criteria.put("sort", sort);
        if (StringUtils.isNotBlank(search)) {
            criteria.put("name", search.trim());
        }

        Map<String, Object> attributeList = new HashMap<>();
        attributeList.put("computerList", this.getComputerListPerPage(criteria));
        attributeList.put("pagination", this.getPage(criteria));
        attributeList.put("maxTotalPage", this.getLastPagePerLimit(criteria));
        attributeList.put("order", order);
        attributeList.put("sort", sort);
        if (StringUtils.isNotBlank(search)) {
            attributeList.put("search", search.trim());
        }
        String url = this.setUrl(search, order, sort);
        if (url.length() > 1) {
            attributeList.put("url", url + "&");

        }
        return new ModelAndView("dashboard").addAllObjects(attributeList);
    }

    @PostMapping("/computer/delete")
    protected RedirectView deleteComputer(@RequestParam(value = "selection") String selection) {
        if (!StringUtils.isBlank(selection)) {
            Arrays.asList(selection.split(",")).stream().forEach(id -> computerService.delete(Integer.parseInt(id)));
        }
        return new RedirectView("/computer/list", true);
    }

    private List<ComputerDTO> getComputerListPerPage(Map<String, String> criteria) {
        Pagination pagination = this.getPage(criteria);
        criteria.put("limit", String.valueOf(pagination.getLimit()));
        criteria.put("offset", String.valueOf(pagination.getLimit() * (pagination.getPage() - 1)));
        return computerService.findByCriteria(criteria).stream().map(c -> computerMapper.mapFromComputerToDTO(c))
                .collect(Collectors.toList());
    }

    private Pagination getPage(Map<String, String> criteria) {
        String pageNumber = criteria.get("pageNumber");
        String perPage = criteria.get("perPage");

        Pagination page = new Pagination(0);
        page.setTotalItem(this.getTotalItem(criteria));
        if (pageNumber != null && StringUtils.isNumeric(pageNumber) && perPage != null
                && StringUtils.isNumeric(perPage)) {
            page.setPage(Integer.parseInt(pageNumber));
            page.setLimit(Integer.parseInt(perPage));
        }
        return page;
    }

    private List<Integer> getLastPagePerLimit(Map<String, String> criteria) {
        int totalItem = this.getTotalItem(criteria);
        int pageLimitMin = new Pagination(Pagination.PageLimit.MIN.getLimit(), 0, totalItem).getTotalPage();
        int pageLimitMid = new Pagination(Pagination.PageLimit.MID.getLimit(), 0, totalItem).getTotalPage();
        int pageLimitMax = new Pagination(Pagination.PageLimit.MAX.getLimit(), 0, totalItem).getTotalPage();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }

    private int getTotalItem(Map<String, String> criteria) {
        Map<String, String> nameToSearch = criteria.entrySet().stream().filter(x -> x.getKey() == "name")
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<? extends Object> computerList = computerService.findByCriteria(nameToSearch);
        return computerList == null ? 0 : computerList.size();
    }

    private String setUrl(String search, String order, String sort) {
        String url = "?";
        if (StringUtils.isNotBlank(search)) {
            url += "search=" + search;
        }
        if (StringUtils.isNotBlank(sort)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "sort=" + sort;
        }
        if (StringUtils.isNotBlank(order)) {
            if (url.length() > 1) {
                url += "&";
            }
            url += "order=" + order;
        }
        return url;
    }
}
