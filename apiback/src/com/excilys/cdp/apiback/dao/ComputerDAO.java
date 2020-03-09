package com.excilys.cdp.apiback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdp.apiback.model.Company;
import com.excilys.cdp.apiback.model.Computer;
import com.excilys.cdp.apiback.model.Computer.ComputerBuilder;
import com.excilys.cdp.apiback.persistence.MySqlConnection;
import com.excilys.cdp.apiback.service.Pagination;

public class ComputerDAO {
	
	private ComputerDAO() {}
	
	private static Connection connection = MySqlConnection.getInstance().getConnection();
	
	private static final String FIND_BY_ID = "SELECT com.id, com.name, com.introduced, com.discontinued, com.company_id FROM computer com WHERE com.id = ?";
	private static final String FIND_ALL = "SELECT com.id, com.name, com.introduced, com.discontinued, com.company_id FROM computer com";
	private static final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduce = ?, discontinued = ?, company_id = ? WHERE id = ? ";
	private static final String DELETE_COMPUTER = "DELETE computer WHERE id = ?";
	
	public static Optional<Computer> findById(int id) {
		Computer computer = null;
		ResultSet result;
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computer = ComputerDAO.setObject(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		Optional<Computer> opt = Optional.ofNullable(computer); 
		return opt;
	}
	
	public static List<Computer> getList() {	
		List<Computer> computerList = new ArrayList<>();
		ResultSet result;
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)){
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computerList.add(ComputerDAO.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computerList;
	}
	
	public static List<Computer> getListPerPage(Pagination page) {	
		List<Computer> computerList = new ArrayList<>();
		ResultSet result;
		String withLimit = " LIMIT " + page.getLimit() * (page.getPage() - 1) + "," + page.getLimit();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL + withLimit)) {
			result = preparedStatement.executeQuery();
			while(result.next()) {
				computerList.add(ComputerDAO.setObject(result));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computerList;
	}

	public static Computer create(Computer computer) {
		ResultSet result;
		int i = 1;
		try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMPUTER)) {
			preparedStatement.setString(i++, computer.getName());
			preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
			preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
			
			if (computer.getCompany() != null) {
				preparedStatement.setInt(i++, computer.getCompany().getId());
			}
			
			preparedStatement.executeUpdate();
			result = preparedStatement.getGeneratedKeys();
			if (result.next()) {
				computer.setId(result.getInt(1));
			}
			System.out.println(computer.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computer;
	}
	
	public static Computer update(Computer computer) {
		int i = 1;
		try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPUTER)) {
			preparedStatement.setString(i++, computer.getName());
			preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getIntroduced()));
			preparedStatement.setDate(i++, java.sql.Date.valueOf(computer.getDiscontinued()));
			
			if (computer.getCompany() != null) {
				preparedStatement.setInt(i++, computer.getCompany().getId());
			}
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return computer;
	}
	
	public boolean delete(int computerId) {
		boolean deleted = false;
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPUTER)) {
			preparedStatement.setInt(1, computerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return deleted;
	}
	
	private static Computer setObject(ResultSet result) throws SQLException {
		ComputerBuilder builder = new Computer.ComputerBuilder();

		builder.setId(result.getInt("com.id"));
		
		if (result.getString("com.name") != null) {
			builder.setName(result.getString("com.name"));
		}

		if (result.getDate("com.introduced") != null) {
			builder.setIntroduced(result.getDate("com.introduced").toLocalDate());
		}
		
		if (result.getDate("com.discontinued") != null) {
			builder.setDiscontinued(result.getDate("com.discontinued").toLocalDate());
		}
		
		if (result.getInt("com.company_id") != 0) {
//			Company company = new Company.CompanyBuilder().setId(result.getInt("com.company_id")).build();
			Company company = CompanyDAO.findById(result.getInt("com.company_id")).get();
			builder.setCompany(company);
		}
		
		Computer computer = builder.build();
		return computer;
	}

}
