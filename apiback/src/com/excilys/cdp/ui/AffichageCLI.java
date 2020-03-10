package com.excilys.cdp.ui;

import java.sql.SQLException;

import com.excilys.cdp.apiback.model.Company;
import com.excilys.cdp.apiback.service.CompanyService;
import com.excilys.cdp.apiback.service.ComputerService;

public class AffichageCLI {
	
	public static void start() {
		
	}

	public static void main(String[] args) throws SQLException {
//		// list computers
//		List<Computer> computerList = ComputerService.getListComputers();
//		
//		if (computerList != null && !computerList.isEmpty()) {
//			for (Computer com : computerList) {
//				System.out.println(com.toString());
//			}
//		}
//		
//		// list companies
//		List<Company> companyList = CompanyService.getListCompanies();
//		if (companyList != null && !companyList.isEmpty()) {
//			for (Company com : companyList) {
//				System.out.println(com.toString());
//			}
//		}
//		
//		// show computer details
//		Computer computer = ComputerService.showDetail(1);
//		System.out.println(computer.toString());
//		if (computer.getCompany() != null) {
//			System.out.println(computer.getCompany().toString());
//		}


		Company companyToInsert = CompanyService.showDetail(1);
		if (companyToInsert != null) {
//			Computer computer1 = new Computer.ComputerBuilder()
//					.setCompany(companyToInsert)
//					.setName("Mon nouveau Super PC")
//					.setIntroduced(LocalDate.now().withDayOfYear(1))
//					.setDiscontinued(LocalDate.now())
//					.build();
//			Computer newComputer = ComputerService.create(computer1);
//			System.out.println(newComputer.toString());
			
//			Computer computerToEdit = ComputerService.showDetail(576);
//			computerToEdit.setCompany(CompanyService.showDetail(1));
//			Computer editedComputer = ComputerService.update(computerToEdit);
//			System.out.println(editedComputer.toString());
		}
		
		ComputerService.remove(ComputerService.showDetail(577));
		
		// getListPerPage
//		Pagination page = new Pagination(2);
//		List<Computer> computerListPerPage = computerDAO.getListPerPage(page);
//		
//		if (computerListPerPage != null && !computerListPerPage.isEmpty()) {
//			for (Computer com : computerListPerPage) {
//				System.out.println(com.toString());
//			}
//		}
		
	}

}
