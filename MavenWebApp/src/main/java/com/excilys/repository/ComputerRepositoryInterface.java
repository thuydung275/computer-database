package com.excilys.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.excilys.model.Computer;

public interface ComputerRepositoryInterface {
    List<Computer> findAll();

    List<Computer> findByCriteria(Map<String, String> criteria);

    Optional<Computer> findById(int id);

    Computer create(Computer computer);

    Computer update(Computer computer);

    boolean delete(Integer id);
}
