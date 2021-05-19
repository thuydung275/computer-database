package daoTest;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.*;
import com.excilys.model.*;
import com.excilys.service.Pagination;

public class ComputerDAOTest {
	private static Logger log = LoggerFactory.getLogger(ComputerDAOTest.class);
	private static ComputerDAO computerInstance;
	
	private static final int TOTAL_COMPUTER = 574;
    private static final int FIND_COMPUTER_BY_ID = 13;
    private static final String FIND_COMPUTER_BY_NAME = "MacBook Pro";
    private static final int FIRST_COMPUTER_PER_PAGE = 1;
    private static final int LAST_COMPUTER_PER_PAGE = 25;
    private static final String NEW_COMPUTER_NAME = "computer name for test";
    private static final String UPDATE_COMPUTER_NAME = "computer name updated for test";
    
    @BeforeClass
    public static void init() throws Exception {
    	computerInstance = ComputerDAO.getInstance();
	}
    
    @Test
	public void testFindByCriteria() {
    	// find by id
    	Optional<Computer> opt = computerInstance.findById(FIND_COMPUTER_BY_ID);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPUTER_BY_ID, opt.get().getId());
			Assert.assertNotNull(opt.get().getCompany());
		} else {
			Assert.fail("Company ID: " + FIND_COMPUTER_BY_ID + " not found !");
		}
		
		opt = computerInstance.findById(TOTAL_COMPUTER + 1);
		Assert.assertFalse(opt.isPresent());
		
		// find by name
		opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPUTER_BY_NAME, opt.get().getName());
		} else {
			Assert.fail("Computer name: " + FIND_COMPUTER_BY_NAME + " not found !");
		}
		
		opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME + FIND_COMPUTER_BY_NAME);
		Assert.assertFalse(opt.isPresent());
    }
    
    @Test
	public void testGetList() {
    	// get total computer
		List<Computer> computerList = computerInstance.getList();
		if (!computerList.isEmpty()) {
			Assert.assertEquals(TOTAL_COMPUTER, computerList.size());
		} else {
			Assert.fail("Fails to find total number of computer !");
		}
		
		// get list per page
		Pagination page = new Pagination(computerList.size());
		List<Computer> computerListPerPage = computerInstance.getListPerPage(page);
		if (!computerListPerPage.isEmpty()) {
			Assert.assertEquals(page.getLimit(), computerListPerPage.size());
			Assert.assertEquals(FIRST_COMPUTER_PER_PAGE, computerListPerPage.get(0).getId());
			Assert.assertEquals(LAST_COMPUTER_PER_PAGE, computerListPerPage.get(page.getLimit() - 1).getId());
		} else {
			Assert.fail("Fails to find company list per page !");
		}
    }
    
    @Test
	public void testCreateUpdateDelete() {
    	List<Computer> computerList = computerInstance.getList();
    	
    	// create
    	Computer computerToCreate = new Computer.ComputerBuilder().setName(NEW_COMPUTER_NAME).build();
    	Computer createdComputer = computerInstance.create(computerToCreate);
		if (createdComputer != null) {
			Assert.assertEquals(computerList.size() + 1, createdComputer.getId());
		} else {
			Assert.fail("Fails to create new computer !");
		}
		
		//update
		createdComputer.setName(UPDATE_COMPUTER_NAME);
		computerInstance.update(createdComputer);
		Optional<Computer> opt = computerInstance.findByName(UPDATE_COMPUTER_NAME);
		if(opt.isPresent()) {
			Assert.assertEquals(UPDATE_COMPUTER_NAME, opt.get().getName());
		} else {
			Assert.fail("Fails to update computer !");
		}
		
		//delete
		boolean deleted = computerInstance.delete(opt.get().getId());
		Assert.assertTrue(deleted);
		
		deleted = computerInstance.delete(TOTAL_COMPUTER + 1);
		Assert.assertFalse(deleted);
    }
}
