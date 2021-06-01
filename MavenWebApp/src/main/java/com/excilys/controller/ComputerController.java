package com.excilys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.Pagination;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.CustomException;

public class ComputerController {

    private static ComputerService computerService = new ComputerService();
    private static Logger log = Logger.getLogger(ComputerController.class);

    public static List<ComputerDTO> getComputerListPerPage(Map<String, String> criteria) {
        Pagination pagination = ComputerController.getPage(criteria);
        criteria.put("limit", pagination.getLimit() * (pagination.getPage() - 1) + "," + pagination.getLimit());
        List<Computer> computerList = computerService.findByCriteria(criteria);

        return computerList.stream().map(c -> ComputerMapper.mapFromComputerToDTO(c)).collect(Collectors.toList());
    }

    public static List<Integer> getMaxPagePerLimit(Map<String, String> criteria) {
        int totalItem = 0;
        if (criteria.get("name") != null && StringUtils.isNotBlank(criteria.get("name"))) {
            Map<String, String> nameToSearch = criteria.entrySet().stream().filter(x -> x.getKey() == "name")
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            totalItem = computerService.findByCriteria(nameToSearch).size();
        } else {
            totalItem = computerService.getListComputers().size();
        }
        int pageLimitMin = new Pagination(Pagination.PageLimit.MIN.getLimit(), 0, totalItem).getTotalPage();
        int pageLimitMid = new Pagination(Pagination.PageLimit.MID.getLimit(), 0, totalItem).getTotalPage();
        int pageLimitMax = new Pagination(Pagination.PageLimit.MAX.getLimit(), 0, totalItem).getTotalPage();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }

    public static Pagination getPage(Map<String, String> criteria) {
        String pageNumber = criteria.get("pageNumber");
        String perPage = criteria.get("perPage");
        String name = criteria.get("name");

        Pagination page = new Pagination(0);

        if (StringUtils.isBlank(name)) {
            page.setTotalItem(computerService.getListComputers().size());
        } else {
            Map<String, String> nameToSearch = criteria.entrySet().stream().filter(x -> x.getKey() == "name")
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            page.setTotalItem(computerService.findByCriteria(nameToSearch).size());
        }
        if (pageNumber != null && StringUtils.isNumeric(pageNumber)) {
            page.setPage(Integer.parseInt(pageNumber));
        }
        if (perPage != null && StringUtils.isNumeric(perPage)) {
            page.setLimit(Integer.parseInt(perPage));
        }
        return page;
    }

    public static void createComputer(String name, String introduced, String discontinued, String companyId)
            throws CustomException {
        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder().withName(name).withIntroduced(introduced)
                .withDiscontinued(discontinued).withCompanyId(companyId).build();
        ComputerValidator.validateComputerDTO(computerDTO);
        Computer computer = ComputerMapper.mapFromDTOtoComputer(computerDTO);
        new ComputerService().create(computer);
    }

    public static void updateComputer(String computerId, String computerName, String introduced, String discontinued,
            String companyId) {
        ComputerDTO computerDTO = ComputerController.findComputer(computerId);
        if (computerDTO == null) {
            throw new CustomException("Id invalid !");
        }
        computerDTO = new ComputerDTO.ComputerDTOBuilder().withId(computerId).withName(computerName)
                .withIntroduced(introduced).withDiscontinued(discontinued).withCompanyId(companyId).build();
        ComputerValidator.validateComputerDTO(computerDTO);
        Computer computer = ComputerMapper.mapFromDTOtoComputer(computerDTO);
        new ComputerService().update(computer);
    }

    public static ComputerDTO findComputer(String computerId) {
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            Computer computer = computerService.findById(Integer.parseInt(computerId));
            return ComputerMapper.mapFromComputerToDTO(computer);
        }
        return null;
    }

    public static boolean deleteComputer(String idList) {
        boolean result = false;
        if (StringUtils.isBlank(idList)) {
            return result;
        } else {
            Arrays.asList(idList.split(",")).stream().forEach(
                    id -> computerService.remove(new Computer.ComputerBuilder().withId(Integer.valueOf(id)).build()));
            result = true;
        }
        return result;
    }

    public static String setUrl(String search, String order, String sort) {
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
