package com.excilys.cdb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.Criteria;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/computer")
public class ComputerController {

    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private static Logger log = Logger.getLogger(ComputerController.class);

    public ComputerController(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping(value = "/edit/{id}")
    public @ResponseBody ResponseEntity<ComputerDTO> editComputer(@PathVariable Integer id) {
        ComputerDTO computer = computerMapper.mapFromComputerToDTO(computerService.findById(id));
        return new ResponseEntity<ComputerDTO>(computer, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public @ResponseBody ResponseEntity<ComputerDTO> saveComputer(@RequestBody ComputerDTO computer) {
        try {
            computer = computerMapper.mapFromComputerToDTO(
                    computerService.createOrUpdate(computerMapper.mapFromDTOtoComputer(computer)));
        } catch (Exception e) {
            // TODO create ResponseMessage object
            log.error(e.getMessage());
            return new ResponseEntity<ComputerDTO>(computer, HttpStatus.OK);
        }
        return new ResponseEntity<ComputerDTO>(computer, HttpStatus.CREATED);
    }

    @PostMapping(value = "/list")
    public @ResponseBody ResponseEntity<List<ComputerDTO>> getComputerList(@RequestBody Criteria criteria) {

        // TODO: replace the map by object
        Map<String, String> attributeList = new HashMap<>();
        attributeList.put("limit", criteria.getLimit());
        attributeList.put("offset", criteria.getOffset());
        attributeList.put("search", criteria.getSearch());
        attributeList.put("sort", criteria.getSort());
        attributeList.put("order", criteria.getSort());

        List<ComputerDTO> computerDTOList = computerService.findByCriteria(attributeList).stream()
                .map(c -> computerMapper.mapFromComputerToDTO(c)).collect(Collectors.toList());
        computerDTOList.forEach(System.out::println);
        return new ResponseEntity<List<ComputerDTO>>(computerDTOList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{ids}")
    public @ResponseBody ResponseEntity<String> deleteComputer(@PathVariable List<Integer> ids) {
        try {
            ids.forEach(id -> computerService.delete(id));
        } catch (Exception ex) {
            return new ResponseEntity<String>(Boolean.valueOf(false).toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>(Boolean.valueOf(true).toString(), HttpStatus.OK);
    }
}
