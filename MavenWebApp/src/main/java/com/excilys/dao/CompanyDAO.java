package com.excilys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.connection.DBConnection;
import com.excilys.model.*;
import com.excilys.model.Company.CompanyBuilder;
import com.excilys.service.Pagination;

/**
 * 
 * @author thuydung
 *
 */
public class CompanyDAO{
	
	private static Logger log = LoggerFactory.getLogger(CompanyDAO.class);
	private static Connection connection = DBConnection.getInstance();
	private static CompanyDAO companyDAO;
	
	private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
	private static final String FIND_BY_NAME = "SELECT company.id, company.name FROM company WHERE company.name LIKE ?";
	private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
	private static final String CREATE_COMPANY = "INSERT INTO company (name) VALUES (?)";
	private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String DELETE_COMPANY = "DELETE company WHERE id = ?";
	
	private CompanyDAO() {}
	
	public static synchronized CompanyDAO getInstance() {
        if (companyDAO == null) {
        	companyDAO = new CompanyDAO();
        }
        return companyDAO;
    }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Company> findById(int id) {
		Company company = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = connection.prepareStatement(FIND_BY_ID);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				company = this.setObject(result);
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		
		Optional<Company> opt = Optional.ofNullable(company); 
		return opt;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Company> findByName(String name) {
		Company company = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = connection.prepareStatement(FIND_BY_NAME);
			preparedStatement.setString(1, name);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				company = this.setObject(result);
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		
		Optional<Company> opt = Optional.ofNullable(company); 
		return opt;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Company> getList() {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Company> companyList = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement(FIND_ALL);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(this.setObject(result));
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		
		return companyList;
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Company> getListPerPage(Pagination page) {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<Company> companyList = new ArrayList<>();
		String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
		
		try {
			preparedStatement = connection.prepareStatement(FIND_ALL + withLimit);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(this.setObject(result));
			}
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		return companyList;
	}

	/**
	 * 
	 * @param company
	 * @return
	 */
	public Company create(Company company) {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			preparedStatement = connection.prepareStatement(CREATE_COMPANY);
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			int lastInsertedId = result.next() ? result.getInt(1) : null;
			company.setId(lastInsertedId);
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement, result);
        }
		return company;
	}
	
	/**
	 * 
	 * @param company
	 * @return
	 */
	public Company update(Company company) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(UPDATE_COMPANY);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement);
        }
		return company;
	}
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	public boolean delete(int companyId) {
		boolean deleted = false;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(DELETE_COMPANY);
			preparedStatement.setInt(1, companyId);
			preparedStatement.executeUpdate();
			deleted = true;
		} catch (SQLException sqle) {
			log.debug(sqle.getMessage());
		} finally {
			DBConnection.closeSqlResources(preparedStatement);
        }
		
		return deleted;
	}
	
	/**
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	private Company setObject(ResultSet result) throws SQLException {
		CompanyBuilder builder = new Company.CompanyBuilder();

		builder.setId(result.getInt("company.id"));
		
		if (result.getString("company.name") != null) {
			builder.setName(result.getString("company.name"));
		}
		
		Company Company = builder.build();
		return Company;
	}

}
