package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;

public class CompanyMapper {
    /**
     *
     * @param result
     * @return Company
     * @throws SQLException
     */
    public static Company setObject(ResultSet result) throws SQLException {
        CompanyBuilder builder = new Company.CompanyBuilder();

        builder.withId(result.getInt("company.id"));

        if (result.getString("company.name") != null) {
            builder.withName(result.getString("company.name"));
        }

        Company company = builder.build();
        return company;
    }
}
