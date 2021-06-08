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

import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;
import com.excilys.service.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CompanyDAOTest {

    @Autowired
    private CompanyDAO companyInstance;
    private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final int TOTAL_COMPANY = 42;
    private static final String FIND_COMPANY_BY_NAME = "IBM";
    private static final Integer FIND_COMPANY_BY_ID = 13;
    private static final Integer FIRST_COMPANY_PER_PAGE = 1;
    private static final Integer LAST_COMPANY_PER_PAGE = 10;
    private static final String NEW_COMPANY_NAME = "company name for test";
    private static final String UPDATE_COMPANY_NAME = "company name updated for test";

    @Test
    public void testFindByIdShouldReturnId() {
        Optional<Company> opt = companyInstance.findById(FIND_COMPANY_BY_ID);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPANY_BY_ID, opt.get().getId());
        } else {
            Assert.fail("Company ID: " + FIND_COMPANY_BY_ID + " not found !");
        }
    }

    @Test
    public void testFindByNameShouldReturnName() {
        Optional<Company> opt = companyInstance.findByName(FIND_COMPANY_BY_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPANY_BY_NAME, opt.get().getName());
        } else {
            Assert.fail("Company name: " + FIND_COMPANY_BY_NAME + " not found !");
        }
    }

    @Test
    public void testFindByNameShouldReturnNull() {
        Optional<Company> opt = companyInstance.findByName(FIND_COMPANY_BY_NAME + FIND_COMPANY_BY_NAME);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testGetCompanyListShouldReturnTotalCompany() {
        List<Company> companyList = companyInstance.getList();
        if (!companyList.isEmpty()) {
            Assert.assertEquals(TOTAL_COMPANY, companyList.size());
        } else {
            Assert.fail("Fails to find total number of company !");
        }
    }

    @Test
    public void testGetCompanyListPerPageShouldReturnCompanyListPerPage() {
        List<Company> companyList = companyInstance.getList();

        Pagination page = new Pagination(companyList.size());
        List<Company> companyListPerPage = companyInstance.getListPerPage(page);
        if (!companyListPerPage.isEmpty()) {
            Assert.assertEquals((int) page.getLimit(), companyListPerPage.size());
            Assert.assertEquals(FIRST_COMPANY_PER_PAGE, companyListPerPage.get(0).getId());
            Assert.assertEquals(LAST_COMPANY_PER_PAGE, companyListPerPage.get(page.getLimit() - 1).getId());
        } else {
            Assert.fail("Fails to find company list per page !");
        }
    }

    @Test
    public void testCreateCompanyShouldReturnIdCreated() {
        Company companyToCreate = new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build();
        Company createdCompany = companyInstance.create(companyToCreate);
        Optional<Company> createdCompanyFromDB = companyInstance.findByName(NEW_COMPANY_NAME);
        if (createdCompanyFromDB.isPresent()) {
            Assert.assertEquals(createdCompany.getId(), createdCompanyFromDB.get().getId());
            companyInstance.delete(createdCompany.getId());
        } else {
            Assert.fail("Fails to create new company !");
        }
    }

    @Test
    public void testUpdateCompanyShouldReturnCompanyUpdated() {
        Company createdCompany = companyInstance
                .create(new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build());
        createdCompany.setName(UPDATE_COMPANY_NAME);
        companyInstance.update(createdCompany);
        Optional<Company> opt = companyInstance.findByName(UPDATE_COMPANY_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(UPDATE_COMPANY_NAME, opt.get().getName());
            companyInstance.delete(createdCompany.getId());
        } else {
            Assert.fail("Fails to update company !");
        }
    }

    @Test
    public void testDeleteCompanyShouldReturnTrue() {
        companyInstance.create(new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build());
        Optional<Company> opt = companyInstance.findByName(NEW_COMPANY_NAME);
        boolean deleted = companyInstance.delete(opt.get().getId());
        Assert.assertTrue(deleted);
    }

}
