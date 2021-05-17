package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Company.CompanyBuilder;
import com.excilys.model.Computer.ComputerBuilder;

public class ComputerMapper {
	/**
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	public static Computer setObject(ResultSet result) throws SQLException {
		ComputerBuilder builder = new Computer.ComputerBuilder();

		builder.setId(result.getInt("computer.id"));
		
		if (result.getString("computer.name") != null) {
			builder.setName(result.getString("computer.name"));
		}

		if (result.getDate("computer.introduced") != null) {
			builder.setIntroduced(result.getDate("computer.introduced").toLocalDate());
		}
		
		if (result.getDate("computer.discontinued") != null) {
			builder.setDiscontinued(result.getDate("computer.discontinued").toLocalDate());
		}
		
		// hydrate Company
		if (result.getInt("computer.company_id") != 0) {
			CompanyBuilder companyBuilder = new Company.CompanyBuilder().setId(result.getInt("computer.company_id"));
			if (result.getString("company.name") != null) {
				companyBuilder.setName(result.getString("company.name"));
			}
			Company company = companyBuilder.build();
			builder.setCompany(company);
		}
		
		Computer computer = builder.build();
		return computer;
	}
}
