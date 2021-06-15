package com.excilys.service;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.excilys.aop.proxy.TimedInvocationHandler;
import com.excilys.model.Computer;
import com.excilys.repository.ComputerRepository;
import com.excilys.repository.ComputerRepositoryInterface;
import com.excilys.validator.CustomException;

/**
 *
 * @author thuydung
 *
 */
@Service
public class ComputerService {

    private ComputerRepositoryInterface computerRepositoryInterface;
    private static Logger log = Logger.getLogger(ComputerService.class);

    public ComputerService(ComputerRepository computerRepository) {
        ComputerRepositoryInterface computerRepositoryInterface = (ComputerRepositoryInterface) Proxy.newProxyInstance(
                ComputerRepositoryInterface.class.getClassLoader(), new Class[] { ComputerRepositoryInterface.class },
                new TimedInvocationHandler(computerRepository));
        this.computerRepositoryInterface = computerRepositoryInterface;
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

    public Computer update(Computer computer) {
        Optional<Computer> computerFromDB = computerRepositoryInterface.findById(computer.getId());
        if (!computerFromDB.isPresent()) {
            throw new CustomException("Computer does not exist in our database", CustomException.ER_NOT_FOUND);
        }
        return computerRepositoryInterface.update(computer);

    }

    public boolean delete(Integer id) {
        return computerRepositoryInterface.delete(id);
    }
}
