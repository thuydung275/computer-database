package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.service.Pagination;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author thuydung
 *
 */
@Repository
public class CompanyDAO {

    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private HikariDataSource dataSource;
    private static Logger log = Logger.getLogger(CompanyDAO.class);

    private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
    private static final String FIND_BY_NAME = "SELECT company.id, company.name FROM company WHERE company.name LIKE ?";
    private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
    private static final String CREATE_COMPANY = "INSERT INTO company (name) VALUES (?)";
    private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ? ";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE company_id = ?";

    private CompanyDAO() {
    }

    public static CompanyDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final CompanyDAO INSTANCE = new CompanyDAO();
    }

    /**
     *
     * @param id
     * @return Optional<Company>
     */
    public Optional<Company> findById(int id) {
        Company company = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                company = companyMapper.mapFromResultSetToCompany(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }

        Optional<Company> opt = Optional.ofNullable(company);
        return opt;
    }

    /**
     *
     * @param name
     * @return Optional<Company>
     */
    public Optional<Company> findByName(String name) {
        Company company = null;

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                company = companyMapper.mapFromResultSetToCompany(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }

        Optional<Company> opt = Optional.ofNullable(company);
        return opt;
    }

    /**
     *
     * @return List<Company>
     */
    public List<Company> getList() {
        List<Company> companyList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                companyList.add(companyMapper.mapFromResultSetToCompany(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }

        return companyList;
    }

    /**
     *
     * @param page
     * @return List<Company>
     */
    public List<Company> getListPerPage(Pagination page) {
        List<Company> companyList = new ArrayList<>();
        String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + withLimit)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                companyList.add(companyMapper.mapFromResultSetToCompany(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return companyList;
    }

    /**
     *
     * @param company
     * @return Company
     */
    public Company create(Company company) {

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            int lastInsertedId = result.next() ? result.getInt(1) : null;
            company.setId(lastInsertedId);
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return company;
    }

    /**
     *
     * @param company
     * @return Company
     */
    public Company update(Company company) {

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return company;
    }

    /**
     *
     * @param companyId
     * @return boolean
     */
    public boolean delete(int companyId) {
        boolean deleted = false;

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement computerPs = connection.prepareStatement(DELETE_COMPUTER);
                    PreparedStatement companyPs = connection.prepareStatement(DELETE_COMPANY)) {
                computerPs.setInt(1, companyId);
                companyPs.setInt(1, companyId);
                computerPs.executeUpdate();
                if (companyPs.executeUpdate() == 1) {
                    deleted = true;
                }
            } catch (SQLException sqle) {
                connection.rollback();
                log.error(sqle.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException sqle) {
            log.error(sqle.getMessage());
        }
        return deleted;
    }
}
