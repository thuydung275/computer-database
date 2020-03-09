package com.excilys.cdp.ui;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdp.apiback.dao.*;
import com.excilys.cdp.apiback.model.*;
import com.excilys.cdp.apiback.service.Pagination;

public class TestClass {

	public static void main(String[] args) throws SQLException {
		
//		Computer computer = computerDao.findById(1);
//		System.out.println(computer);
		
//		List<Computer> computerList = computerDao.getList();
//		
//		if (computerList != null && !computerList.isEmpty()) {
//			for (Computer com : computerList) {
//				System.out.println(com.toString());
//			}
//		}
		
//		Pagination page = new Pagination(2);
//		List<Computer> computerListPerPage = computerDao.getListPerPage(page);
//		
//		if (computerListPerPage != null && !computerListPerPage.isEmpty()) {
//			for (Computer com : computerListPerPage) {
//				System.out.println(com.toString());
//			}
//		}
		
		Company company = new Company.CompanyBuilder().setName("test").build();
		CompanyDAO.create(company);
		
		
		
	}

}
