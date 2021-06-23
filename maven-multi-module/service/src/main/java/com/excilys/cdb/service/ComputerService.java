package com.excilys.cdb.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.repository.ComputerRepository;

/**
 *
 * @author thuydung
 *
 */
@Service
public class ComputerService {

    private ComputerRepository computerRepositoryInterface;
    private static Logger log = Logger.getLogger(ComputerService.class);

    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepositoryInterface = computerRepository;
    }

    public List<Computer> getList() {
        return computerRepositoryInterface.findAll();
    }

    public List<Computer> findByCriteria(Map<String, String> criteria) {
        return computerRepositoryInterface.findByCriteria(criteria);
    }

    public Computer findById(int id) {
        Optional<Computer> opt = computerRepositoryInterface.findById(id);
        return opt.map(c -> c).orElse(null);
    }

    public Computer create(Computer computer) {
        return computerRepositoryInterface.create(computer);
    }

    public Computer update(Computer computer) throws Exception {
        Optional<Computer> computerFromDB = computerRepositoryInterface.findById(computer.getId());
        if (!computerFromDB.isPresent()) {
            throw new Exception("Computer does not exist in our database");
        }
        return computerRepositoryInterface.update(computer);
    }

    public Computer createOrUpdate(Computer computer) throws Exception {
        if (computer.getId() != null) {
            return this.update(computer);
        }
        return this.create(computer);
    }

    public boolean delete(Integer id) {
        return computerRepositoryInterface.delete(id);
    }
}
