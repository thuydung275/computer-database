package com.excilys.cdb.servlet;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/computer/edit")
public class ModifyComputerServlet {

    private CompanyService companyService;
    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private CompanyMapper companyMapper;
    private ComputerValidator computerValidator;
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(ModifyComputerServlet.class);

    public ModifyComputerServlet(CompanyService companyService, ComputerService computerService,
            ComputerMapper computerMapper, CompanyMapper companyMapper, ComputerValidator computerValidator) {
        this.companyService = companyService;
        this.computerService = computerService;
        this.computerMapper = computerMapper;
        this.companyMapper = companyMapper;
        this.computerValidator = computerValidator;
    }

    @GetMapping
    protected ModelAndView getView(@RequestParam(value = "id", required = false) String computerId) {
        ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder().build();
        if (computerId != null && StringUtils.isNumeric(computerId)) {
            computerDTO = computerMapper.mapFromComputerToDTO(computerService.findById(Integer.parseInt(computerId)));
        }
        ModelAndView modelAndView = new ModelAndView("modifyComputer", "computer", computerDTO);
        List<CompanyDTO> companyDTOList = companyService.getList().stream()
                .map(c -> companyMapper.mapFromCompanyToDTO(c)).collect(Collectors.toList());
        modelAndView.addObject("companyList", companyDTOList);
        return modelAndView;
    }

    @PostMapping
    protected RedirectView editComputer(@ModelAttribute("computer") ComputerDTO computerDTO,
            RedirectAttributes redirectAttributes) {
        try {
            String successMessage = " was successfully ";
            if (computerDTO.getId() != null) {
                computerDTO = this.updateComputer(computerDTO);
                successMessage += "updated !";
            } else {
                computerDTO = this.createComputer(computerDTO);
                successMessage += "added !";
            }
            redirectAttributes.addFlashAttribute("success", computerDTO.getName() + successMessage);
            redirectAttributes.addAttribute("id", computerDTO.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return new RedirectView("/computer/edit", true);
    }

    private ComputerDTO createComputer(ComputerDTO computerDTO) throws Exception {
        computerValidator.validateComputerDTO(computerDTO);
        return computerMapper
                .mapFromComputerToDTO(computerService.create(computerMapper.mapFromDTOtoComputer(computerDTO)));
    }

    private ComputerDTO updateComputer(ComputerDTO computerDTO) throws Exception {
        computerValidator.validateComputerDTO(computerDTO);
        return computerMapper
                .mapFromComputerToDTO(computerService.update(computerMapper.mapFromDTOtoComputer(computerDTO)));
    }
}
