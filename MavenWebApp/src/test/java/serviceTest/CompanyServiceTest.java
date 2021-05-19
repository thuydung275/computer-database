package serviceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.*;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.helper.Constant;
import com.excilys.helper.CustomException;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.Pagination;

public class CompanyServiceTest {
	
	private static Logger log = Logger.getLogger(CompanyServiceTest.class);
	
	@Mock
	CompanyDAO companyDao;
	
	@InjectMocks
	CompanyService companyService;
	
	private Company trueCompany;
	private static final int FAKE_COMPANY_ID = 1242354;
    private static final String FAKE_COMPANY_NAME = "fake name";
	
	@BeforeClass
	public void init() {
		MockitoAnnotations.initMocks(this);
		trueCompany = new Company.CompanyBuilder().setId(1).setName("Apple Inc.").build();
	}
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testFindCompany() {
		
		// find company by id
		assertThat(companyService.findById(trueCompany.getId()), is(trueCompany));

		exceptionRule.expect(CustomException.class);
	    exceptionRule.expectMessage(FAKE_COMPANY_ID + Constant.TEXT_ER_NOT_FOUND);
		companyService.findById(FAKE_COMPANY_ID);
		
		// find company by name
		assertThat(companyService.findByName(trueCompany.getName()), is(trueCompany));
		
		exceptionRule.expectMessage(FAKE_COMPANY_NAME + Constant.TEXT_ER_NOT_FOUND);
		companyService.findByName(FAKE_COMPANY_NAME);
	}
	
	@Test
	public void testGetListCompanies() {
		List<Company> companyList = companyService.getListCompanies();
		assertFalse(companyList.isEmpty());
	}
	
	//@Test
	public void testGetListCompaniesPerPage() {
		List<Company> companyList = companyService.getListCompanies();
		Pagination pagination = new Pagination(companyList.size());
		
		List<Company> firstPage = companyService.getListPerPage(pagination);
		assertTrue(firstPage.size() == pagination.getLimit());
		assertTrue(firstPage.get(0).getId() == 1);
		
		pagination.next();
		
		List<Company> secondPage = companyService.getListPerPage(pagination);
		assertTrue(secondPage.get(0).getId() == pagination.getLimit() + 1);
	}
}
