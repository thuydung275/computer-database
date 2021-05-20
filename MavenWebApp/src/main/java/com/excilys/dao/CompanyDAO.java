package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.excilys.connection.ConnectionHelper;
import com.excilys.connection.DBConnection;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.service.Pagination;

/**
 *
 * @author thuydung
 *
 */
public class CompanyDAO {

    private static Logger log = Logger.getLogger(CompanyDAO.class);
    private static BasicDataSource dataSource = (BasicDataSource) DBConnection.getInstance().getDataSource();
    private static Connection connection;
    private static ResultSet result;
    private static PreparedStatement preparedStatement;

    private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
    private static final String FIND_BY_NAME = "SELECT company.id, company.name FROM company WHERE company.name LIKE ?";
    private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
    private static final String CREATE_COMPANY = "INSERT INTO company (name) VALUES (?)";
    private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ? ";
    private static final String DELETE_COMPANY = "DELETE company WHERE id = ?";

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

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                company = CompanyMapper.setObject(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
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

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                company = CompanyMapper.setObject(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
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

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                companyList.add(CompanyMapper.setObject(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
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

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL + withLimit);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                companyList.add(CompanyMapper.setObject(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return companyList;
    }

    /**
     *
     * @param company
     * @return Company
     */
    public Company create(Company company) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_COMPANY, preparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();
            int lastInsertedId = result.next() ? result.getInt(1) : null;
            company.setId(lastInsertedId);
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return company;
    }

    /**
     *
     * @param company
     * @return Company
     */
    public Company update(Company company) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMPANY);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, company.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
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

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_COMPANY);
            preparedStatement.setInt(1, companyId);
            if (preparedStatement.executeUpdate() == 1) {
                deleted = true;
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return deleted;
    }

}
