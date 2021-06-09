package com.excilys.repository;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.validator.CustomException;

@Repository
public class CompanyRepository {

    private JdbcTemplate jdbcTemplate;
    private CompanyMapper companyMapper;
    private static Logger log = Logger.getLogger(CompanyRepository.class);

    private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
    private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ?";

    public CompanyRepository(JdbcTemplate jdbcTemplate, CompanyMapper companyMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.companyMapper = companyMapper;
    }

    public List<Company> findAll() {
        try {
            return this.jdbcTemplate.query(FIND_ALL, companyMapper);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public Optional<Company> findById(int id) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(FIND_BY_ID, companyMapper, id));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    @Transactional
    public boolean delete(int idCompany) {
        boolean deleted = false;
        try {
            jdbcTemplate.update(DELETE_COMPUTER, idCompany);
            int statut = jdbcTemplate.update(DELETE_COMPANY, idCompany);
            if (statut == 1) {
                deleted = true;
            } else {
                throw new CustomException("Fail to delete company !");
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return deleted;
    }
}
