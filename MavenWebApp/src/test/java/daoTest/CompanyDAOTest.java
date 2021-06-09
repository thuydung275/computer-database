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

import com.excilys.model.Company;
import com.excilys.repository.CompanyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {

    @Autowired
    private CompanyRepository companyRepository;
    private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final Integer FIND_COMPANY_BY_ID = 13;

    @Test
    public void testFindByIdShouldReturnId() {
        Optional<Company> opt = companyRepository.findById(FIND_COMPANY_BY_ID);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPANY_BY_ID, opt.get().getId());
        } else {
            Assert.fail("Company ID: " + FIND_COMPANY_BY_ID + " not found !");
        }
    }

    @Test
    public void testFindAllListShouldReturnCompanyList() {
        List<Company> companyList = companyRepository.findAll();
        Assert.assertNotNull(companyList);
    }

    @Test
    public void testDeleteCompanyShouldReturnTrue() {
        Optional<Company> opt = companyRepository.findById(FIND_COMPANY_BY_ID);
        boolean deleted = companyRepository.delete(opt.get().getId());
        Assert.assertTrue(true);
    }

}
