package com.excilys.cdp.apiback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdp.apiback.model.*;
import com.excilys.cdp.apiback.model.Company.CompanyBuilder;
import com.excilys.cdp.apiback.persistence.MySqlConnection;
import com.excilys.cdp.apiback.service.Pagination;

/**
 * 
 * @author thuydung
 *
 */
public class CompanyDAO{
	
	private static Connection connection = MySqlConnection.getInstance().getConnection();
	private static CompanyDAO INSTANCE = null;
	
	private static final String FIND_BY_ID = "SELECT company.id, company.name FROM company WHERE company.id = ?";
	private static final String FIND_ALL = "SELECT company.id, company.name FROM company";
	private static final String CREATE_COMPANY = "INSERT INTO company (name) VALUES (?)";
	private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String DELETE_COMPANY = "DELETE company WHERE id = ?";
	
	private CompanyDAO() {}
	
	public static synchronized CompanyDAO getInstance() {
        if ( INSTANCE == null ) {
        	INSTANCE = new CompanyDAO();
        }
        return INSTANCE;
    }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Company> findById(int id) {
		Company company = null;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				company = this.setObject(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Optional<Company> opt = Optional.ofNullable(company); 
		return opt;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Company> getList() {
		List<Company> companyList = new ArrayList<>();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(this.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return companyList;
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Company> getListPerPage(Pagination page) {
		List<Company> companyList = new ArrayList<>();
		String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + withLimit);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(this.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return companyList;
	}

	/**
	 * 
	 * @param company
	 * @return
	 */
	public Company create(Company company) {
		try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY)) {
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
			ResultSet result = preparedStatement.getGeneratedKeys();
			int lastInsertedId = result.next() ? result.getInt(1) : null;
			company.setId(lastInsertedId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return company;
	}
	
	/**
	 * 
	 * @param company
	 * @return
	 */
	public Company update(Company company) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY);
			preparedStatement.setInt(1, companyId);
			preparedStatement.executeUpdate();
			deleted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
