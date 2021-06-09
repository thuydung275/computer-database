package com.excilys.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

@Repository
public class ComputerRepository {

    private JdbcTemplate jdbcTemplate;
    private ComputerMapper computerMapper;
    private static Logger log = Logger.getLogger(ComputerRepository.class);

    private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
    private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ";
    private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";

    public ComputerRepository(JdbcTemplate jdbcTemplate, ComputerMapper computerMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.computerMapper = computerMapper;
    }

    public List<Computer> findAll() {
        try {
            return this.jdbcTemplate.query(FIND_ALL, computerMapper);
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
                return this.jdbcTemplate.query(query, computerMapper, "%" + criteria.get("name") + "%",
                        "%" + criteria.get("name") + "%");
            } else {
                return this.jdbcTemplate.query(query, computerMapper);
            }
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public Optional<Computer> findById(int id) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(FIND_BY_ID, computerMapper, id));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public Computer create(Computer computer) {
        Date introduced = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date discontinued = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued())
                : null;
        Integer companyId = computer.getCompany() != null ? computer.getCompany().getId() : null;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            this.jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(CREATE_COMPUTER, Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                ps.setString(i++, computer.getName());
                ps.setDate(i++, introduced);
                ps.setDate(i++, discontinued);
                ps.setObject(i++, companyId);
                return ps;
            }, keyHolder);
            computer.setId(keyHolder.getKey().intValue());
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return computer;
    }

    public Computer update(Computer computer) {
        Integer companyId = computer.getCompany() != null ? computer.getCompany().getId() : null;
        Date introduced = computer.getIntroduced() != null ? java.sql.Date.valueOf(computer.getIntroduced()) : null;
        Date discontinued = computer.getDiscontinued() != null ? java.sql.Date.valueOf(computer.getDiscontinued())
                : null;
        try {
            this.jdbcTemplate.update(UPDATE_COMPUTER, computer.getName(), introduced, discontinued, companyId,
                    computer.getId());
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return computer;
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
