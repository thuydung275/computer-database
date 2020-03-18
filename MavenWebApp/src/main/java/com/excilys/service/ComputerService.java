package com.excilys.service;

import java.util.List;
import java.util.Optional;

import com.excilys.dao.ComputerDAO;
import com.excilys.helper.Constant;
import com.excilys.helper.CustomException;
import com.excilys.model.Company;
import com.excilys.model.Computer;

/**
 * 
 * @author thuydung
 *
 */
public class ComputerService {
	
	private static ComputerDAO computerInstance = ComputerDAO.getInstance();
	private static CompanyService companyService  = new CompanyService();
	
	/**
	 * 
	 * @return
	 */
	public List<Computer> getListComputers() {
		return computerInstance.getList();
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public List<Computer> getListPerPage(Pagination page) {
		return computerInstance.getListPerPage(page);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Computer findById(int id) {
		Optional<Computer> opt = computerInstance.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CustomException(id + Constant.TEXT_ER_NOT_FOUND, Constant.ER_NOT_FOUND);
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public Computer create(Computer computer) {
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (computer.getDiscontinued().compareTo(computer.getIntroduced()) < 0){
				throw new CustomException("discontinued date is smaller then introduced date", Constant.ER_INTRODUCED_BIGGER_DISCONTINUED);
			}
		} else if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			throw new CustomException("introduced date is null while discontinued date is not null", Constant.ER_DISCONTINUED_NULL_INTRODUCED_NOT_NULL);
		}
		if (computer.getCompany() != null) {
			if (computer.getCompany().getId() != 0) {
				Company company = companyService.findById(computer.getCompany().getId());
			} else {
				throw new CustomException("company does not exist in our database", Constant.ER_NOT_FOUND);
			}
		}
		return computerInstance.create(computer);	
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public Computer update(Computer computer) {
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
		return computerInstance.update(computer);
		
	}
	
	/**
	 * 
	 * @param computer
	 * @return
	 */
	public boolean remove(Computer computer) {
		Optional<Computer> computerFromDB = computerInstance.findById(computer.getId());
		if (!computerFromDB.isPresent()) {
			throw new CustomException("Computer does not exist in our database", Constant.ER_NOT_FOUND);
		}
		return computerInstance.delete(computer.getId());
	}

}
