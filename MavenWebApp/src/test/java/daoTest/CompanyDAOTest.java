package daoTest;

import java.util.List;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;
import com.excilys.service.Pagination;

public class CompanyDAOTest {
	
	private static Logger log = LoggerFactory.getLogger(CompanyDAOTest.class);
	private static CompanyDAO companyInstance;
	
	private static final int TOTAL_COMPANY = 42;
    private static final String FIND_COMPANY_BY_NAME = "IBM";
    private static final int FIND_COMPANY_BY_ID = 13;
    private static final int FIRST_COMPANY_PER_PAGE = 1;
    private static final int LAST_COMPANY_PER_PAGE = 25;
    private static final String NEW_COMPANY_NAME = "company name for test";
    private static final String UPDATE_COMPANY_NAME = "company name updated for test";
	
	@BeforeClass
    public static void init() throws Exception {
		companyInstance = CompanyDAO.getInstance();
	}
	
	@Test
	public void testFindByCriteria() {
		// find by id
		Optional<Company> opt = companyInstance.findById(FIND_COMPANY_BY_ID);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPANY_BY_ID, opt.get().getId());
		} else {
			Assert.fail("Company ID: " + FIND_COMPANY_BY_ID + " not found !");
		}
		
		opt = companyInstance.findById(TOTAL_COMPANY + 1);
		Assert.assertFalse(opt.isPresent());
		
		// find by name
		opt = companyInstance.findByName(FIND_COMPANY_BY_NAME);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPANY_BY_NAME, opt.get().getName());
		} else {
			Assert.fail("Company name: " + FIND_COMPANY_BY_NAME + " not found !");
		}
		
		opt = companyInstance.findByName(FIND_COMPANY_BY_NAME + FIND_COMPANY_BY_NAME);
		Assert.assertFalse(opt.isPresent());
	}
	
	@Test
	public void testGetList() {
		// get total company
		List<Company> companyList = companyInstance.getList();
		if (!companyList.isEmpty()) {
			Assert.assertEquals(TOTAL_COMPANY, companyList.size());
		} else {
			Assert.fail("Fails to find total number of company !");
		}
		
		// get list per page
		Pagination page = new Pagination(companyList.size());
		List<Company> companyListPerPage = companyInstance.getListPerPage(page);
		if (!companyListPerPage.isEmpty()) {
			Assert.assertEquals(page.getLimit(), companyListPerPage.size());
			Assert.assertEquals(FIRST_COMPANY_PER_PAGE, companyListPerPage.get(0).getId());
			Assert.assertEquals(LAST_COMPANY_PER_PAGE, companyListPerPage.get(page.getLimit() - 1).getId());
		} else {
			Assert.fail("Fails to find company list per page !");
		}
	}
	
	@Test
	public void testCreateUpdateDelete() {
		List<Company> companyList = companyInstance.getList();
		
		// create
		Company companyToCreate = new Company.CompanyBuilder().setName(NEW_COMPANY_NAME).build();
		Company createdCompany = companyInstance.create(companyToCreate);
		if (createdCompany != null) {
			Assert.assertEquals(companyList.size() + 1, createdCompany.getId());
		} else {
			Assert.fail("Fails to create new company !");
		}
		
		// update
		createdCompany.setName(UPDATE_COMPANY_NAME);
		companyInstance.update(createdCompany);
		Optional<Company> opt = companyInstance.findByName(UPDATE_COMPANY_NAME);
		if(opt.isPresent()) {
			Assert.assertEquals(UPDATE_COMPANY_NAME, opt.get().getName());
		} else {
			Assert.fail("Fails to update company !");
		}
		
		//delete
		boolean deleted = companyInstance.delete(opt.get().getId());
		Assert.assertTrue(deleted);
		
		deleted = companyInstance.delete(TOTAL_COMPANY + 1);
		Assert.assertFalse(deleted);
	}
	
	@AfterClass
    public static void terminate() throws Exception {
        companyInstance = null;
        System.setProperty("test","false");
    }

}
