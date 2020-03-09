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

public class CompanyDAO{
	
	private CompanyDAO () {}
	
	private static Connection connection = MySqlConnection.getInstance().getConnection();
	
	private static final String FIND_BY_ID = "SELECT compa.id, compa.name FROM company compa com WHERE compa.id = ?";
	private static final String FIND_ALL = "SELECT compa.id, compa.name FROM company compa";
	private static final String CREATE_COMPANY = "INSERT INTO company (name) VALUES (?)";
	private static final String UPDATE_COMPANY = "UPDATE company SET name = ? WHERE id = ? ";
	private static final String DELETE_COMPANY = "DELETE company WHERE id = ?";
	
	public static Optional<Company> findById(int id) {
		Company company = null;
		ResultSet result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				company = CompanyDAO.setObject(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Optional<Company> opt = Optional.ofNullable(company); 
		return opt;
	}
	
	public static List<Company> getList() {
		List<Company> companyList = new ArrayList<>();
		ResultSet result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
			result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(CompanyDAO.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return companyList;
	}
	
	public static List<Company> getListPerPage(Pagination page) {
		List<Company> companyList = new ArrayList<>();
		ResultSet result;
		String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + withLimit)) {
			result = preparedStatement.executeQuery();
			while(result.next()) {
				companyList.add(CompanyDAO.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return companyList;
	}

	public static Company create(Company company) {
		ResultSet result;
		try(PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPANY)) {
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				company.setId(result.getInt(1));
			}
			System.out.println(company.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return company;
	}
	
	public static Company update(Company company) {
		try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY)) {
			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return company;
	}
	
	public static boolean delete(int companyId) {
		boolean deleted = false;
		
		try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY)) {
			preparedStatement.setInt(1, companyId);
			preparedStatement.executeUpdate();
			deleted = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return deleted;
	}
	
	private static Company setObject(ResultSet result) throws SQLException {
		CompanyBuilder builder = new Company.CompanyBuilder();

		builder.setId(result.getInt("compa.id"));
		
		if (result.getString("compa.name") != null) {
			builder.setName(result.getString("compa.name"));
		}
		
		Company Company = builder.build();
		return Company;
	}

}
