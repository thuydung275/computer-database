package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.Pagination;
import com.zaxxer.hikari.HikariDataSource;

/**
 *
 * @author thuydung
 *
 */
@Repository
public class ComputerDAO {

    @Autowired
    private ComputerMapper computerMapper;
    @Autowired
    private HikariDataSource dataSource;

    private static Logger log = Logger.getLogger(ComputerDAO.class);

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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                computer = computerMapper.mapFromResultSetToComputer(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                computer = computerMapper.mapFromResultSetToComputer(result);
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                computerList.add(computerMapper.mapFromResultSetToComputer(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + withLimit)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                computerList.add(computerMapper.mapFromResultSetToComputer(result));
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return computerList;
    }

    /**
     *
     * @param Map<String, String> criteria = new HashMap<String, String>() {{
     *                    put("name", "CM"); put("order", "ASC"); put("sort",
     *                    "computer.name"); put("limit", "0,5"); }};
     * @return List<Computer>
     */
    public List<Computer> findByCriteria(Map<String, String> criteria) {
        List<Computer> computerList = new ArrayList<>();
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
        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            if (criteria.containsKey("name") && StringUtils.isNotBlank(criteria.get("name"))) {
                preparedStatement.setString(1, "%" + criteria.get("name") + "%");
                preparedStatement.setString(2, "%" + criteria.get("name") + "%");
            }
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    computerList.add(computerMapper.mapFromResultSetToComputer(result));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return computerList;
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer create(Computer computer) {

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPUTER,
                        Statement.RETURN_GENERATED_KEYS)) {
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
            ResultSet result = preparedStatement.getGeneratedKeys();

            int lastInsertedId = result.next() ? result.getInt(1) : null;
            computer.setId(lastInsertedId);
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return computer;
    }

    /**
     *
     * @param computer
     * @return Computer
     */
    public Computer update(Computer computer) {

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPUTER)) {
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

        try (Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPUTER)) {
            preparedStatement.setInt(1, computerId);
            if (preparedStatement.executeUpdate() == 1) {
                deleted = true;
            }
        } catch (SQLException sqle) {
            log.debug(sqle.getMessage());
        }
        return deleted;
    }

}
