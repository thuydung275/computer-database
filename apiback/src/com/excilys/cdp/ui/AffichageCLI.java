package com.excilys.cdp.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.excilys.cdp.apiback.model.*;
import com.excilys.cdp.apiback.model.Computer.ComputerBuilder;
import com.excilys.cdp.apiback.service.*;

public class AffichageCLI {
	
	private static Scanner scanner = new Scanner(System.in);

	public static void start()  {

		boolean continuer = true;
		
		while(continuer == true) {
			printWelcomeBoard();
			printMenu();
			
			int choice = getChoice();
			
			switch(choice) {
			case 1: 
				System.out.println("case 1");
				List<Computer> computerList = ComputerService.getListComputers();
				if (!computerList.isEmpty()) {
					for (Computer computer: computerList) {
						System.out.println(computer.toString());
					}
				}
				break;
			case 2: 
				System.out.println("case 2");
				List<Company> companyList = CompanyService.getListCompanies();
				if (!companyList.isEmpty()) {
					for (Company company: companyList) {
						System.out.println(company.toString());
					}
				}
				break;
			case 3: 
				System.out.println("case 3");
				int id = getComputerId();
				Computer computer = ComputerService.showDetail(id);
				System.out.println(computer.toString());
				break;
			case 4: 
				Computer inputComputer = getComputerInput();
				System.out.println(inputComputer);
				System.out.println("case 4");
				break;
			case 5: 
				System.out.println("case 5");
				break;
			case 6: 
				System.out.println("case 6");
				break;
			case 7: 
				System.out.println("case 7");
				continuer = false;
				break;
			default: 
				System.out.println("default");
				break;
			}
			
		}
		
		
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
		
		// show computer details
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
		
//		ComputerService.remove(ComputerService.showDetail(577));
		
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
	
	private static void printWelcomeBoard() {
		System.out.println(
				"|￣￣￣￣￣￣￣￣￣￣|\n" + 
				"|     WELCOME    |\n" + 
				"|    	ON       | \n" + 
				"|     BOARD      |\n" + 
				"| ＿＿＿＿＿＿＿＿＿| \n" + 
				"(\\__/) || \n" + 
				"(•ㅅ•)   || \n" + 
				"/ 　 づ");
		System.out.println("--------------------------------------------");
	}
	
	private static void printMenu() {
		System.out.println("Please select one option from 1 to 7: ");
		System.out.println("1 - List computers");
		System.out.println("2 - List companies");
		System.out.println("3 - Show computer details");
		System.out.println("4 - Create a computer");
		System.out.println("5 - Update a computer");
		System.out.println("6 - Delete a computer");
		System.out.println("7 - Quit");
		System.out.println("--------------------------------------------");
	}
	
	private static void printGoodByeMessage() {
		System.out.println(
				"			      _     _\n" + 
				"                             ( \\---/ )\n" + 
				"                              ) . . (\n" + 
				"________________________,--._(___Y___)_,--._______________________ see you sooooon\n" + 
				"                        `--'           `--'");
	}
	
	private static int getChoice() {
		String answer = scanner.next();
		scanner.nextLine();
		
		if (answer.length() != 1) {
			printMenu();
			getChoice();
		}
		
		int choice =  Integer.parseInt(answer);
		if (choice < 1 || choice > 7) {
			printMenu();
			getChoice();
		}
		return choice;
	}
	
	private static int getComputerId() {
		System.out.println("Computer id: \n");
		String answer = scanner.next();
		scanner.nextLine();
		return Integer.parseInt(answer);
	}
	
	private static Computer getComputerInput() {
		ComputerBuilder builder = new Computer.ComputerBuilder();
		
		System.out.println("Computer name: \n");
		String computerName = scanner.nextLine();
		builder.setName(computerName);
		
		System.out.println("Computer introduced date(dd/mm/yyyy): \n");
		String computerIntroducedDate = scanner.nextLine();
		if (computerIntroducedDate.length() == 10) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			formatter = formatter.withLocale(Locale.FRANCE);
			LocalDate date = LocalDate.parse(computerIntroducedDate, formatter);
			builder.setIntroduced(date);
		} 
		
		System.out.println("Computer discontinued date(dd/mm/yyyy): \n");
		String computerDiscontinuedDate = scanner.nextLine();
		if (computerDiscontinuedDate.length() == 10) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			formatter = formatter.withLocale(Locale.FRANCE);
			LocalDate date = LocalDate.parse(computerDiscontinuedDate, formatter);
			builder.setDiscontinued(date);
		} 
		
		System.out.println("Select a company from the list below: \n");
		List<Company> companyList = CompanyService.getListCompanies();
		for (Company company : companyList) {
			System.out.println(company.getName());
		}
		String companyName = scanner.nextLine();
		System.out.println(companyName);
		Company company = CompanyService.findByName(companyName);
		System.out.println(company.toString());
		if (company != null) {
			builder.setCompany(company);
		}

		Computer newComputer = builder.build();
		return newComputer;
	}

}
