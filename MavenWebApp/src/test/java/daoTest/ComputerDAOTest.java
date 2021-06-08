package daoTest;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.dao.ComputerDAO;
import com.excilys.model.Computer;
import com.excilys.service.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {

    @Autowired
    private ComputerDAO computerInstance;
    private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final int TOTAL_COMPUTER = 574;
    private static final Integer FIND_COMPUTER_BY_ID = 13;
    private static final String FIND_COMPUTER_BY_NAME = "MacBook Pro";
    private static final Integer FIRST_COMPUTER_PER_PAGE = 1;
    private static final Integer LAST_COMPUTER_PER_PAGE = 10;
    private static final String NEW_COMPUTER_NAME = "computer name for test";
    private static final String UPDATE_COMPUTER_NAME = "computer name updated for test";

    @Test
    public void testFindByIdShouldReturnComputer() {
        Optional<Computer> opt = computerInstance.findById(FIND_COMPUTER_BY_ID);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPUTER_BY_ID, opt.get().getId());
            Assert.assertNotNull(opt.get().getCompany());
        } else {
            Assert.fail("Company ID: " + FIND_COMPUTER_BY_ID + " not found !");
        }
    }

    @Test
    public void testFindByIdShouldReturnNull() {
        Optional<Computer> opt = computerInstance.findById(TOTAL_COMPUTER + 1);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testFindByNameShouldReturnComputer() {
        Optional<Computer> opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPUTER_BY_NAME, opt.get().getName());
        } else {
            Assert.fail("Computer name: " + FIND_COMPUTER_BY_NAME + " not found !");
        }
    }

    @Test
    public void testFindByNameShouldReturnNull() {
        Optional<Computer> opt = computerInstance.findByName(FIND_COMPUTER_BY_NAME + FIND_COMPUTER_BY_NAME);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testGetComputerListShouldReturnComputerList() {
        List<Computer> computerList = computerInstance.getList();
        if (!computerList.isEmpty()) {
            Assert.assertEquals(TOTAL_COMPUTER, computerList.size());
        } else {
            Assert.fail("Fails to find total number of computer !");
        }
    }

    @Test
    public void testGetComputerListPerPageShouldReturnComputerListPerPage() {
        List<Computer> computerList = computerInstance.getList();

        Pagination page = new Pagination(computerList.size());
        List<Computer> computerListPerPage = computerInstance.getListPerPage(page);
        if (!computerListPerPage.isEmpty()) {
            Assert.assertEquals((int) page.getLimit(), computerListPerPage.size());
            Assert.assertEquals(FIRST_COMPUTER_PER_PAGE, computerListPerPage.get(0).getId());
            Assert.assertEquals(LAST_COMPUTER_PER_PAGE, computerListPerPage.get(page.getLimit() - 1).getId());
        } else {
            Assert.fail("Fails to find company list per page !");
        }
    }

    @Test
    public void testCreateComputerShouldReturnComputerCreated() {
        Computer computerToCreate = new Computer.ComputerBuilder().withName(NEW_COMPUTER_NAME).build();
        Computer createdComputer = computerInstance.create(computerToCreate);
        Optional<Computer> createdComputerFromDB = computerInstance.findByName(NEW_COMPUTER_NAME);
        if (createdComputerFromDB.isPresent()) {
            Assert.assertEquals(createdComputer.getId(), createdComputerFromDB.get().getId());
            computerInstance.delete(createdComputer.getId());
        } else {
            Assert.fail("Fails to create new computer !");
        }
    }

    @Test
    public void testUpdateComputerShouldReturnComputerUpdated() {
        Computer computerToCreate = new Computer.ComputerBuilder().withName(NEW_COMPUTER_NAME).build();
        Computer createdComputer = computerInstance.create(computerToCreate);
        createdComputer.setName(UPDATE_COMPUTER_NAME);
        computerInstance.update(createdComputer);
        Optional<Computer> opt = computerInstance.findByName(UPDATE_COMPUTER_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(UPDATE_COMPUTER_NAME, opt.get().getName());
        } else {
            Assert.fail("Fails to update computer !");
        }
    }

    @Test
    public void testDeleteComputerShouldReturnTrue() {
        computerInstance.create(new Computer.ComputerBuilder().withName(NEW_COMPUTER_NAME).build());
        Optional<Computer> opt = computerInstance.findByName(NEW_COMPUTER_NAME);
        boolean deleted = computerInstance.delete(opt.get().getId());
        Assert.assertTrue(deleted);
    }

    @Test
    public void testDeleteComputerShouldReturnFalse() {
        boolean deleted = computerInstance.delete(TOTAL_COMPUTER + 1);
        Assert.assertFalse(deleted);
    }
}
