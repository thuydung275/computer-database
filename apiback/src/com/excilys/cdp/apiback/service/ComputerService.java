package com.excilys.cdp.apiback.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdp.apiback.dao.ComputerDAO;
import com.excilys.cdp.apiback.helper.Constant;
import com.excilys.cdp.apiback.helper.CustomException;
import com.excilys.cdp.apiback.model.Computer;

/**
 * 
 * @author thuydung
 *
 */
public class ComputerService {
	
	private static ComputerDAO computerInstance = ComputerDAO.getInstance();
	
	/**
	 * 
	 * @return
	 */
	public static List<Computer> getListComputers() {
		return computerInstance.getList();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Computer showDetail(int id) {
		Optional<Computer> opt = computerInstance.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CustomException("Computer does not exist in our database", Constant.ER_NOT_FOUND);
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public static Computer create(Computer computer) {
		System.out.println("==============");
		System.out.println("Checking if computer " + computer.getName() + " is eligible to be created");
		
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getDiscontinued().compareTo(computer.getIntroduced()) < 0){
				throw new CustomException("discontinued date is smaller then introduced date", Constant.ER_INTRODUCED_BIGGER_DISCONTINUED);
			}
		} else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			throw new CustomException("introduced date is null while discontinued date is not null", Constant.ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL);
		}
		
		System.out.println("==============");
		System.out.println("Creating computer " + computer.toString());
		System.out.println("=============="); 
		
		return computerInstance.create(computer);	
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public static Computer update(Computer computer) {
		System.out.println("==============");
		System.out.println("Checking if computer " + computer.getName() + " is eligible to be updated");
		Optional<Computer> computerFromDB = computerInstance.findById(computer.getId());
		if (!computerFromDB.isPresent()) {
			throw new CustomException("Computer does not exist in our database", Constant.ER_NOT_FOUND);
		}
		
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getDiscontinued().compareTo(computer.getIntroduced()) < 0){
				throw new CustomException("discontinued date is smaller then introduced date", Constant.ER_INTRODUCED_BIGGER_DISCONTINUED);
			}
		} else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			throw new CustomException("introduced date is null while discontinued date is not null", Constant.ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL);
		}
		
		System.out.println("==============");
		System.out.println("Updating computer " + computer.toString());
		System.out.println("==============");
		return computerInstance.update(computer);
		
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public static boolean remove(Computer computer) {
		System.out.println("==============");
		System.out.println("Checking if computer " + computer.getName() + " is eligible to be removed");
		Optional<Computer> computerFromDB = computerInstance.findById(computer.getId());
		if (!computerFromDB.isPresent()) {
			throw new CustomException("Computer does not exist in our database", Constant.ER_NOT_FOUND);
		}
		return computerInstance.delete(computer.getId());
		
	}

}
