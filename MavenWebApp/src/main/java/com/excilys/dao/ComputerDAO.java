package com.excilys.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static Logger log = LoggerFactory.getLogger(ComputerDAO.class);
	private static DBConnection connection = DBConnection.getInstance();
	private static ComputerDAO computerDAO;
	
	private static final String FIND_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String FIND_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id ";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ? ";
	private static final String DELETE_COMPUTER = "DELETE FROM computer WHERE id = ?";
	
	private ComputerDAO() {}
	
	public static synchronized ComputerDAO getInstance() {
        if (computerDAO == null) {
        	computerDAO = new ComputerDAO();
        }
        return computerDAO;
    }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Computer> findById(int id) {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Computer computer = null;
		
		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(FIND_BY_ID);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computer = ComputerMapper.setObject(result);
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		Optional<Computer> opt = Optional.ofNullable(computer); 
		return opt;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Computer> getList() {	
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Computer> computerList = new ArrayList<>();

		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(FIND_ALL);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computerList.add(ComputerMapper.setObject(result));
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		return computerList;
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Computer> getListPerPage(Pagination page) {	
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Computer> computerList = new ArrayList<>();
		String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
		
		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(FIND_ALL + withLimit);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computerList.add(ComputerMapper.setObject(result));
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		return computerList;
	}

	/**
	 * 
	 * @param computer
	 * @return
	 */
	public Computer create(Computer computer) {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(CREATE_COMPUTER);
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
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		return computer;
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public Computer update(Computer computer) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(UPDATE_COMPUTER);
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
			DBConnection.closeSqlResources(preparedStatement);
        }
		return computer;
	}
	
	/**
	 * 
	 * @param computerId
	 * @return
	 */
	public boolean delete(int computerId) {
		boolean deleted = false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.getSQLConnection().prepareStatement(DELETE_COMPUTER);
			preparedStatement.setInt(1, computerId);
			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement);
        }
		return deleted;
	}

}
