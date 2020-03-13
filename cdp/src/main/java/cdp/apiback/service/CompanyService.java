package cdp.apiback.service;

import java.util.List;
import java.util.Optional;

import cdp.apiback.dao.CompanyDAO;
import cdp.apiback.model.Company;

/**
 * 
 * @author thuydung
 *
 */
public class CompanyService {
	
	private static CompanyDAO companyInstance = CompanyDAO.getInstance();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static Company findByName(String name) {
		Optional<Company> opt = companyInstance.findByName(name);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Company showDetail(int id) {
		Optional<Company> opt = companyInstance.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<Company> getListCompanies() {
		return companyInstance.getList();
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	public static List<Company> getListPerPage(Pagination page) {
		return companyInstance.getListPerPage(page);
	}
}
