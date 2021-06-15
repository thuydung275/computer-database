package com.excilys.repository;

import java.util.List;
import java.util.Optional;

import com.excilys.model.Company;

public interface CompanyRepositoryInterface {
    List<Company> findAll();

    Optional<Company> findById(int id);

    boolean delete(int idCompany);
}
