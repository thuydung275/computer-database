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
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.Pagination;

/**
 *
 * @author thuydung
 *
 */
public class ComputerDAO {

    private static Logger log = Logger.getLogger(ComputerDAO.class);
    private static BasicDataSource dataSource = (BasicDataSource) DBConnection.getInstance().getDataSource();
    private static Connection connection;
    private static ResultSet result;
    private static PreparedStatement preparedStatement;

    private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
    private static final String FIND_BY_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ?";
    private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ";
    private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
    private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
    private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";

    private ComputerDAO() {
    }

    public static ComputerDAO getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ComputerDAO INSTANCE = new ComputerDAO();
    }

    /**
     *
     * @param id
     * @return Optional<Computer>
     */
    public Optional<Computer> findById(int id) {
        Computer computer = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                computer = ComputerMapper.setObject(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        Optional<Computer> opt = Optional.ofNullable(computer);
        return opt;
    }

    /**
     *
     * @param name
     * @return Optional<Computer>
     */
    public Optional<Computer> findByName(String name) {
        Computer computer = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                computer = ComputerMapper.setObject(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }

        Optional<Computer> opt = Optional.ofNullable(computer);
        return opt;
    }

    /**
     *
     * @return List<Computer>
     */
    public List<Computer> getList() {
        List<Computer> computerList = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                computerList.add(ComputerMapper.setObject(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return computerList;
    }

    /**
     *
     * @param page
     * @return List<Computer>
     */
    public List<Computer> getListPerPage(Pagination page) {
        List<Computer> computerList = new ArrayList<>();
        String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL + withLimit);
            result = preparedStatement.executeQuery();
            while (result.next()) {
                computerList.add(ComputerMapper.setObject(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return computerList;
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer create(Computer computer) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_COMPUTER, preparedStatement.RETURN_GENERATED_KEYS);
            int i = 1;
            preparedStatement.setString(i++, computer.getName());

            if (computer.getIntroduced() != null) {
                preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
            } else {
                preparedStatement.setNull(i++, 0);
            }

            if (computer.getDiscontinued() != null) {
                preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
            } else {
                preparedStatement.setNull(i++, 0);
            }

            if (computer.getCompany() != null) {
                preparedStatement.setInt(i++, computer.getCompany().getId());
            } else {
                preparedStatement.setNull(i++, 0);
            }

            preparedStatement.executeUpdate();
            result = preparedStatement.getGeneratedKeys();

            int lastInsertedId = result.next() ? result.getInt(1) : null;
            computer.setId(lastInsertedId);
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return computer;
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer update(Computer computer) {

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_COMPUTER);
            int i = 1;
            preparedStatement.setString(i++, computer.getName());

            if (computer.getIntroduced() != null) {
                preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
            } else {
                preparedStatement.setNull(i++, 0);
            }

            if (computer.getDiscontinued() != null) {
                preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
            } else {
                preparedStatement.setNull(i++, 0);
            }

            if (computer.getCompany() != null) {
                preparedStatement.setInt(i++, computer.getCompany().getId());
            } else {
                preparedStatement.setNull(i++, 0);
            }
            preparedStatement.setInt(i++, computer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        } finally {
            ConnectionHelper.closeSqlResources(connection, preparedStatement, result);
        }
        return computer;
    }

    /**
     *
     * @param computerId
     * @return boolean
     */
    public boolean delete(int computerId) {
        boolean deleted = false;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_COMPUTER);
            preparedStatement.setInt(1, computerId);
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
