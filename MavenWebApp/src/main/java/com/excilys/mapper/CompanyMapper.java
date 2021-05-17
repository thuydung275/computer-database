package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;

public class CompanyMapper {
	/**
	 * 
	 * @param result
	 * @return
	 * @throws SQLException
	 */
	public static Company setObject(ResultSet result) throws SQLException {
		CompanyBuilder builder = new Company.CompanyBuilder();

		builder.setId(result.getInt("company.id"));
		
		if (result.getString("company.name") != null) {
			builder.setName(result.getString("company.name"));
		}
		
		Company Company = builder.build();
		return Company;
	}
}
