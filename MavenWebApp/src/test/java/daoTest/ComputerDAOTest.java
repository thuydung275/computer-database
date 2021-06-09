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

import com.excilys.model.Computer;
import com.excilys.repository.ComputerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ComputerDAOTest {

    @Autowired
    private ComputerRepository computerRepository;
    private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final int TOTAL_COMPUTER = 574;
    private static final Integer FIND_COMPUTER_BY_ID = 13;
    private static final String NEW_COMPUTER_NAME = "computer name for test";
    private static final String UPDATE_COMPUTER_NAME = "computer name updated for test";

    @Test
    public void testFindByIdShouldReturnComputer() {
        Optional<Computer> opt = computerRepository.findById(FIND_COMPUTER_BY_ID);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPUTER_BY_ID, opt.get().getId());
            Assert.assertNotNull(opt.get().getCompany());
        } else {
            Assert.fail("Company ID: " + FIND_COMPUTER_BY_ID + " not found !");
        }
    }

    @Test
    public void testFindByIdShouldReturnNull() {
        Optional<Computer> opt = computerRepository.findById(TOTAL_COMPUTER + 1);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testFindAllShouldReturnComputerList() {
        List<Computer> computerList = computerRepository.findAll();
        Assert.assertNotNull(computerList);
    }

    @Test
    public void testCreateComputerShouldReturnTrue() {
        Computer computerToCreate = new Computer.ComputerBuilder().withName(NEW_COMPUTER_NAME).build();
        Assert.assertTrue(computerRepository.create(computerToCreate));
    }

    @Test
    public void testUpdateComputerShouldReturnTrue() {
        Computer computer = computerRepository.findById(FIND_COMPUTER_BY_ID).get();
        computer.setName(UPDATE_COMPUTER_NAME);
        Assert.assertTrue(computerRepository.update(computer));
    }

    @Test
    public void testDeleteComputerShouldReturnTrue() {
        boolean deleted = computerRepository.delete(TOTAL_COMPUTER);
        Assert.assertTrue(deleted);
    }

    @Test
    public void testDeleteComputerShouldReturnFalse() {
        boolean deleted = computerRepository.delete(TOTAL_COMPUTER + 1);
        Assert.assertFalse(deleted);
    }
}
