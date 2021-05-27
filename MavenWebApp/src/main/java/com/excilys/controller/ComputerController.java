package com.excilys.controller;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.Pagination;
import com.excilys.validator.ComputerValidator;
import com.excilys.validator.CustomException;

public class ComputerController {

    private static ComputerService computerService = new ComputerService();

    public static List<ComputerDTO> getComputerListPerPage(Pagination page) {
        ComputerService computerService = new ComputerService();
        List<Computer> computerList = computerService.getListPerPage(page);

        return computerList.stream().map(c -> {
            ComputerDTO comDTO = new ComputerDTO.ComputerDTOBuilder().build();
            if (c.getId() != null) {
                comDTO.setId(c.getId().toString());
            }
            if (c.getName() != null && !c.getName().isEmpty()) {
                comDTO.setName(c.getName());
            }
            if (c.getIntroduced() != null) {
                comDTO.setIntroduced(c.getIntroduced().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            if (c.getDiscontinued() != null) {
                comDTO.setDiscontinued(c.getDiscontinued().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
            if (c.getCompany() != null) {
                comDTO.setCompanyId(c.getCompany().getId().toString());
                comDTO.setCompanyName(c.getCompany().getName());
            }
            return comDTO;
        }).collect(Collectors.toList());
    }

    public static Pagination getPage(String pageNumber, String perPage) {
        Pagination page = new Pagination(0);
        page.setTotalItem(computerService.getListComputers().size());
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
        Computer computer = ComputerMapper.setObject(computerDTO);
        new ComputerService().create(computer);
    }

    public static List<Integer> getMaxPagePerLimit(int limitMin, int limitMid, int limitMax) {
        int totalItem = computerService.getListComputers().size();
        int pageLimitMin = new Pagination(limitMin, 0, totalItem).getTotalPage();
        int pageLimitMid = new Pagination(limitMid, 0, totalItem).getTotalPage();
        int pageLimitMax = new Pagination(limitMax, 0, totalItem).getTotalPage();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }
}
