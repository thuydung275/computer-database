package com.excilys.cdp.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdp.apiback.helper.DateHelper;
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
				printCompanyListPerPage();
				break;
			case SHOW_COMPUTER: 
				showComputerDetails();
				break;
			case CREATE_COMPUTER: 
				createComputer();
				break;
			case UPDATE_COMPUTER: 
				updateComputer();
				break;
			case DELETE_COMPUTER: 
				removeComputer();
				break;
			case QUIT: 
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
		
		System.out.print("Computer name: \n");
		String computerName = scanner.useDelimiter("\n").next();
		builder.setName(computerName);
		
		System.out.print("Computer introduced date(dd/mm/yyyy): \n");
		LocalDate introducedDate = getDate();
		if (introducedDate != null) {
			builder.setIntroduced(introducedDate);
		}
		
		System.out.println("Computer discontinued date(dd/mm/yyyy): \n");
		LocalDate discontinuedDate = getDate();
		if (discontinuedDate != null) {
			builder.setDiscontinued(discontinuedDate);
		}
		
		Company company = getSelectedCompany();
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
	
	private static LocalDate getDate() {
		String computerIntroducedDate = scanner.next();
		if (computerIntroducedDate.length() == 10) {
			LocalDate date = DateHelper.toLocaleDate(computerIntroducedDate);
			return date;
		} 
		return null;
	}
	
	private static Company getSelectedCompany() {
		System.out.print("Select a company from the list below: \n");
		List<Company> companyList = CompanyService.getListCompanies();
		for (Company company : companyList) {
			System.out.println(company.getName());
		}
		String companyName = scanner.useDelimiter("\n").next();
		return CompanyService.findByName(companyName);
	}

	private static void showComputerDetails() {
		int id = getComputerId();
		Computer computer = ComputerService.showDetail(id);
		System.out.println(computer.toString());
	}
	
	private static void createComputer() {
		Computer inputComputer = getComputerInput();
		ComputerService.create(inputComputer);
		System.out.println(inputComputer);
	}
	
	private static void updateComputer() {
		System.out.println("Type the computer id that you want to update");
		List<Computer> computerList = ComputerService.getListComputers();
		for (Computer computer : computerList) {
			System.out.println(computer.toString());
		}
		int id = getComputerId();
		Computer computer = getComputerInput();
		computer.setId(id);
		ComputerService.update(computer);
	}
	
	private static void removeComputer() {
		int id1 = getComputerId();
		Computer computerToRemove = ComputerService.showDetail(id1);
		if (computerToRemove == null) {
			System.out.println("computer not found");
			return;
		}
		String name = computerToRemove.getName();
		boolean deleted = ComputerService.remove(computerToRemove);
		System.out.println("computer " + name + " is removed" + deleted);
	}
}
