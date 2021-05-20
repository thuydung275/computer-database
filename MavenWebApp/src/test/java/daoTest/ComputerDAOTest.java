package daoTest;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Computer;
import com.excilys.service.Pagination;

public class ComputerDAOTest {

	private static ComputerDAO computerInstance;
	private static Logger log = Logger.getLogger(CompanyDAOTest.class);

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
	public void testFindById() {
		Optional<Computer> opt = computerInstance.findById(FIND_COMPUTER_BY_ID);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPUTER_BY_ID, opt.get().getId());
			Assert.assertNotNull(opt.get().getCompany());
		} else {
			Assert.fail("Company ID: " + FIND_COMPUTER_BY_ID + " not found !");
		}

		opt = computerInstance.findById(TOTAL_COMPUTER + 1);
		Assert.assertFalse(opt.isPresent());
	}

	@Test
	public void testFindByName() {
		Optional<Computer> opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME);
		if (opt.isPresent()) {
			Assert.assertEquals(FIND_COMPUTER_BY_NAME, opt.get().getName());
		} else {
			Assert.fail("Computer name: " + FIND_COMPUTER_BY_NAME + " not found !");
		}

		opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME + FIND_COMPUTER_BY_NAME);
		Assert.assertFalse(opt.isPresent());
	}

	@Test
	public void testGetComputerList() {
		List<Computer> computerList = computerInstance.getList();
		if (!computerList.isEmpty()) {
			Assert.assertEquals(TOTAL_COMPUTER, computerList.size());
		} else {
			Assert.fail("Fails to find total number of computer !");
		}
	}

	@Test
	public void testGetComputerListPerPage() {
		List<Computer> computerList = computerInstance.getList();

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
	public void testCreateComputer() {
		Computer computerToCreate = new Computer.ComputerBuilder().setName(NEW_COMPUTER_NAME).build();
		Computer createdComputer = computerInstance.create(computerToCreate);
		Optional<Computer> createdComputerFromDB = computerInstance.findByName(NEW_COMPUTER_NAME);
		if (createdComputerFromDB.isPresent()) {
			Assert.assertEquals(createdComputer.getId(), createdComputerFromDB.get().getId());
			computerInstance.delete(createdComputerFromDB.get().getId());
		} else {
			Assert.fail("Fails to create new computer !");
		}
	}

	@Test
	public void testUpdateComputer() {
		Computer computerToCreate = new Computer.ComputerBuilder().setName(NEW_COMPUTER_NAME).build();
		Computer createdComputer = computerInstance.create(computerToCreate);
		createdComputer.setName(UPDATE_COMPUTER_NAME);
		computerInstance.update(createdComputer);
		Optional<Computer> opt = computerInstance.findByName(UPDATE_COMPUTER_NAME);
		if (opt.isPresent()) {
			Assert.assertEquals(UPDATE_COMPUTER_NAME, opt.get().getName());
			computerInstance.delete(opt.get().getId());
		} else {
			Assert.fail("Fails to update computer !");
		}
	}

	@Test
	public void testDeleteComputer() {
		computerInstance.create(new Computer.ComputerBuilder().setName(NEW_COMPUTER_NAME).build());
		Optional<Computer> opt = computerInstance.findByName(NEW_COMPUTER_NAME);
		boolean deleted = computerInstance.delete(opt.get().getId());
		Assert.assertTrue(deleted);

		deleted = computerInstance.delete(TOTAL_COMPUTER + 1);
		Assert.assertFalse(deleted);
	}

	@AfterClass
	public static void terminate() throws Exception {
		computerInstance = null;
		System.setProperty("test", "false");
	}
}
