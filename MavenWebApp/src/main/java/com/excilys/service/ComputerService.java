package com.excilys.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.model.Computer;
import com.excilys.repository.ComputerRepository;
import com.excilys.validator.CustomException;

/**
 *
 * @author thuydung
 *
 */
@Service
public class ComputerService {

    private ComputerRepository computerRepository;
    private static Logger log = Logger.getLogger(ComputerService.class);

    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    public List<Computer> getList() {
        return computerRepository.findAll();
    }

    public List<Computer> findByCriteria(Map<String, String> criteria) {
        return computerRepository.findByCriteria(criteria);
    }

    public Computer findById(int id) {
        Optional<Computer> opt = computerRepository.findById(id);
        return opt.map(c -> c).orElse(null);
    }

    public Computer create(Computer computer) {
        return computerRepository.create(computer);
    }

    public Computer update(Computer computer) {
        Optional<Computer> computerFromDB = computerRepository.findById(computer.getId());
        if (!computerFromDB.isPresent()) {
            throw new CustomException("Computer does not exist in our database", CustomException.ER_NOT_FOUND);
        }
        return computerRepository.update(computer);

    }

    public boolean delete(Integer id) {
        return computerRepository.delete(id);
    }
}
