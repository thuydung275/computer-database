package com.excilys.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;
import com.excilys.model.Computer;

@Repository
public class ComputerRepository {

    private JdbcTemplate jdbcTemplate;
    private static Logger log = Logger.getLogger(ComputerRepository.class);

    private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
    private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ";
    private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";

    RowMapper<Computer> rowMapper = (rs, rowNum) -> {
        Computer computer = new Computer.ComputerBuilder().build();
        computer.setId(rs.getInt("computer.id"));
        computer.setName(rs.getString("computer.name"));
        if (rs.getDate("computer.introduced") != null) {
            computer.setIntroduced(rs.getDate("computer.introduced").toLocalDate());
        }
        if (rs.getDate("computer.discontinued") != null) {
            computer.setDiscontinued(rs.getDate("computer.discontinued").toLocalDate());
        }
        if (rs.getInt("computer.company_id") != 0) {
            Company company = new Company.CompanyBuilder().withId(rs.getInt("computer.company_id"))
                    .withName(rs.getString("company.name")).build();
            computer.setCompany(company);
        }
        return computer;
    };

    public ComputerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Computer> findAll() {
        try {
            return this.jdbcTemplate.query(FIND_ALL, rowMapper);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public List<Computer> findByCriteria(Map<String, String> criteria) {
        String query = FIND_ALL;

        if (criteria.containsKey("name") && StringUtils.isNotBlank(criteria.get("name"))) {
            query += " WHERE computer.name LIKE ? OR company.name LIKE ? ";
        }
        if (criteria.containsKey("order") && StringUtils.isNotBlank(criteria.get("order"))
                && criteria.containsKey("sort") && StringUtils.isNotBlank(criteria.get("sort"))) {
            query += " ORDER BY " + criteria.get("sort") + " " + criteria.get("order");
        }
        if (criteria.containsKey("limit") && StringUtils.isNotBlank(criteria.get("limit"))) {
            query += " LIMIT " + criteria.get("limit");
        }
        query += ";";
        try {
            if (criteria.containsKey("name") && StringUtils.isNotBlank(criteria.get("name"))) {
                return this.jdbcTemplate.query(query, rowMapper, criteria.get("name"), criteria.get("name"));
            } else {
                return this.jdbcTemplate.query(query, rowMapper);
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public Optional<Computer> findById(int id) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(FIND_BY_ID, rowMapper, id));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public boolean create(Computer computer) {
        Integer companyId = computer.getCompany() != null ? computer.getCompany().getId() : null;
        Date introduced = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date discontinued = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued())
                : null;
        try {
            return this.jdbcTemplate.update(CREATE_COMPUTER, computer.getName(), introduced, discontinued,
                    companyId) == 1;
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    public boolean update(Computer computer) {
        Integer companyId = computer.getCompany() != null ? computer.getCompany().getId() : null;
        Date introduced = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date discontinued = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued())
                : null;
        try {
            return this.jdbcTemplate.update(UPDATE_COMPUTER, computer.getName(), introduced, discontinued, companyId,
                    computer.getId()) == 1;
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    public boolean delete(Integer id) {
        try {
            return this.jdbcTemplate.update(DELETE_COMPUTER, id) == 1;
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

}
