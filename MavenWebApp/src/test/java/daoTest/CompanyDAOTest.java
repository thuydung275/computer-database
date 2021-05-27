package daoTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.connection.DBConnection;
import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;
import com.excilys.service.Pagination;

public class CompanyDAOTest {

    private static CompanyDAO companyInstance = CompanyDAO.getInstance();
    private Connection connection;
    private static Logger log = Logger.getLogger(CompanyDAOTest.class);

    private static final int TOTAL_COMPANY = 42;
    private static final String FIND_COMPANY_BY_NAME = "IBM";
    private static final Integer FIND_COMPANY_BY_ID = 13;
    private static final Integer FIRST_COMPANY_PER_PAGE = 1;
    private static final Integer LAST_COMPANY_PER_PAGE = 10;
    private static final String NEW_COMPANY_NAME = "company name for test";
    private static final String UPDATE_COMPANY_NAME = "company name updated for test";

    @Before
    public void setUp() throws Exception {
        try {
            connection = DBConnection.getInstance().getDataSource().getConnection();
            ScriptRunner sr = new ScriptRunner(connection);
            File sqlScript = new File("src/test/resources/testDB.sql");
            String path = sqlScript.getAbsolutePath();
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    @After
    public void terminate() throws Exception {
        connection.close();
    }

    @Test
    public void testFindById() {
        Optional<Company> opt = companyInstance.findById(FIND_COMPANY_BY_ID);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPANY_BY_ID, opt.get().getId());
        } else {
            Assert.fail("Company ID: " + FIND_COMPANY_BY_ID + " not found !");
        }

        opt = companyInstance.findById(TOTAL_COMPANY + 1);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testFindByName() {
        Optional<Company> opt = companyInstance.findByName(FIND_COMPANY_BY_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(FIND_COMPANY_BY_NAME, opt.get().getName());
        } else {
            Assert.fail("Company name: " + FIND_COMPANY_BY_NAME + " not found !");
        }

        opt = companyInstance.findByName(FIND_COMPANY_BY_NAME + FIND_COMPANY_BY_NAME);
        Assert.assertFalse(opt.isPresent());
    }

    @Test
    public void testGetCompanyList() {
        List<Company> companyList = companyInstance.getList();
        if (!companyList.isEmpty()) {
            Assert.assertEquals(TOTAL_COMPANY, companyList.size());
        } else {
            Assert.fail("Fails to find total number of company !");
        }
    }

    @Test
    public void testGetCompanyListPerPage() {
        List<Company> companyList = companyInstance.getList();

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
    public void testCreateCompany() {
        Company companyToCreate = new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build();
        Company createdCompany = companyInstance.create(companyToCreate);
        Optional<Company> createdCompanyFromDB = companyInstance.findByName(NEW_COMPANY_NAME);
        if (createdCompanyFromDB.isPresent()) {
            Assert.assertEquals(createdCompany.getId(), createdCompanyFromDB.get().getId());
        } else {
            Assert.fail("Fails to create new company !");
        }
    }

    @Test
    public void testUpdateCompany() {
        Company createdCompany = companyInstance
                .create(new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build());
        createdCompany.setName(UPDATE_COMPANY_NAME);
        companyInstance.update(createdCompany);
        Optional<Company> opt = companyInstance.findByName(UPDATE_COMPANY_NAME);
        if (opt.isPresent()) {
            Assert.assertEquals(UPDATE_COMPANY_NAME, opt.get().getName());
        } else {
            Assert.fail("Fails to update company !");
        }
    }

    @Test
    public void testDeleteCompany() {
        companyInstance.create(new Company.CompanyBuilder().withName(NEW_COMPANY_NAME).build());
        Optional<Company> opt = companyInstance.findByName(NEW_COMPANY_NAME);
        boolean deleted = companyInstance.delete(opt.get().getId());
        Assert.assertTrue(deleted);

        deleted = companyInstance.delete(TOTAL_COMPANY + 1);
        Assert.assertFalse(deleted);
    }

}
