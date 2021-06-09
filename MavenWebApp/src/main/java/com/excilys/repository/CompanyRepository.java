package com.excilys.repository;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.model.Company;

@Repository
public class CompanyRepository {

    private JdbcTemplate jdbcTemplate;
    private static Logger log = Logger.getLogger(CompanyRepository.class);

    private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
    private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ?";

    RowMapper<Company> rowMapper = (rs, rowNum) -> {
        Company company = new Company.CompanyBuilder().build();
        company.setId(rs.getInt("company.id"));
        company.setName(rs.getString("company.name"));
        return company;
    };

    public CompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Company> findAll() {
        try {
            return this.jdbcTemplate.query(FIND_ALL, rowMapper);
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public Optional<Company> findById(int id) {
        try {
            return Optional.ofNullable(this.jdbcTemplate.queryForObject(FIND_BY_ID, rowMapper, id));
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return Optional.ofNullable(null);
    }

    public boolean delete(int idCompany) {
        boolean deleted = false;

//        try (Connection connection = dataSource.getConnection()) {
//            connection.setAutoCommit(false);
//            try (PreparedStatement computerPs = connection.prepareStatement(DELETE_COMPUTER);
//                    PreparedStatement companyPs = connection.prepareStatement(DELETE_COMPANY)) {
//                computerPs.setInt(1, companyId);
//                companyPs.setInt(1, companyId);
//                computerPs.executeUpdate();
//                if (companyPs.executeUpdate() == 1) {
//                    deleted = true;
//                }
//            } catch (SQLException sqle) {
//                connection.rollback();
//                log.error(sqle.getMessage());
//            } finally {
//                connection.setAutoCommit(true);
//            }
//
//        } catch (SQLException sqle) {
//            log.error(sqle.getMessage());
//        }
        return deleted;
    }

}
