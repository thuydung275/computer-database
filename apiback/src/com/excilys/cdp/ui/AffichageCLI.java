package com.excilys.cdp.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdp.apiback.model.*;
import com.excilys.cdp.apiback.model.Computer.ComputerBuilder;
import com.excilys.cdp.apiback.service.*;

public class AffichageCLI {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private static Logger logger = LoggerFactory.getLogger(AffichageCLI.class);

	public static void start()  {		
	    logger.info("TestCLI");


		boolean continuer = true;
		
		while(continuer == true) {
			printWelcomeBoard();
			printMenu();
			
			Action choice = getChoice();

			switch(choice) {
			case LIST_COMPUTER: 
				printComputerListPerPage();

				break;
			case LIST_COMPANIES: 
				System.out.println("case 2");
				List<Company> companyList = CompanyService.getListCompanies();
				if (!companyList.isEmpty()) {
					for (Company company: companyList) {
						System.out.println(company.toString());
					}
				}
				printCompanyListPerPage();
				break;
			case SHOW_COMPUTER: 
				int id = getComputerId();
				Computer computer = ComputerService.showDetail(id);
				System.out.println(computer.toString());
				break;
			case CREATE_COMPUTER: 
				Computer inputComputer = getComputerInput();
				System.out.println(inputComputer);
				System.out.println("case 4");
				break;
			case UPDATE_COMPUTER: 
				System.out.println("case 5");
				break;
			case DELETE_COMPUTER: 
				int id1 = getComputerId();
				Computer computerToRemove = ComputerService.showDetail(id1);
				boolean deleted = ComputerService.remove(computerToRemove);
				System.out.println("computer is removed" + deleted);
				break;
			case QUIT: 
				System.out.println("case 7");
				printGoodByeMessage();
				continuer = false;
				break;
			default: 
				System.out.println("default");
				break;
			}
			
		}
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
	
	private static Action getChoice() {
		int answer = scanner.nextInt();
		if (answer < 1 || answer > 7) {
			printMenu();
			getChoice();
		}
		Action choice = Action.getAction(answer);
		return choice;
	}
	
	private static int getComputerId() {
		System.out.println("Computer id: \n");
		return scanner.nextInt();
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
	
	private static void printComputerListPerPage() {
		List<Computer> computerList = ComputerService.getListComputers();
		if (computerList.isEmpty()) {
			System.out.println("No computer found in our database");
		}
		
		Pagination page = new Pagination();
		int totalComputers = computerList.size();
		String direction = null;
		do {
			List<Computer> computerTrunkList = ComputerService.getListComputerPerPage(page);
			
			for (Computer computer: computerTrunkList) {
				System.out.println(computer.toString());
			}
			
			direction = setPage(page, totalComputers);
		} while(!direction.equals("q"));
		
		printMenu();
	}
	
	private static void printCompanyListPerPage() {
		List<Company> companyList = CompanyService.getListCompanies();
		if (companyList.isEmpty()) {
			System.out.println("No company found in our database");
		}
		
		Pagination page = new Pagination();
		int totalCompanies = companyList.size();
		String direction = null;
		do {
			List<Company> companyTrunkList = CompanyService.getListPerPage(page);
			
			for (Company company: companyTrunkList) {
				System.out.println(company.toString());
			}
			
			direction = setPage(page, totalCompanies);
		} while(!direction.equals("q"));
		
		printMenu();
	}
	
	private static String setPage(Pagination page, int total) {
		System.out.println("Page " + page.getPage() + "/" + page.getTotalPage(total));
		String guide = "please type 'q' to quit, ";
		if (page.getPage() > 1) {
			guide += " 's' for the previous page, ";
		} else if (page.getPage() < page.getTotalPage(total)) {
			guide += " 'f' for the next page, ";
		}
		System.out.println(guide + "\n");
		String direction = scanner.next();
		int newPage;
		if (direction.toLowerCase().equals("s") && page.getPage() > 1) {
			page.setPage(page.getPage() - 1);
		}
		if (direction.toLowerCase().equals("f") && page.getPage() < page.getTotalPage(total)) {
			page.setPage(page.getPage() + 1);
		}
		
		return direction;
	}

}
