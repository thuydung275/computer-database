package com.excilys.controller;

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

        return computerList.stream().map(c -> ComputerMapper.mapFromComputerToDTO(c)).collect(Collectors.toList());
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

    public static List<Integer> getMaxPagePerLimit(int limitMin, int limitMid, int limitMax) {
        int totalItem = computerService.getListComputers().size();
        int pageLimitMin = new Pagination(limitMin, 0, totalItem).getTotalPage();
        int pageLimitMid = new Pagination(limitMid, 0, totalItem).getTotalPage();
        int pageLimitMax = new Pagination(limitMax, 0, totalItem).getTotalPage();
        return Arrays.asList(pageLimitMin, pageLimitMid, pageLimitMax);
    }

    public static ComputerDTO findComputer(String computerId) {
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            Computer computer = computerService.findById(Integer.parseInt(computerId));
            return ComputerMapper.mapFromComputerToDTO(computer);
        }
        return null;
    }
}
