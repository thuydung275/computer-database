package serviceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dao.CompanyDAO;
import com.excilys.helper.Constant;
import com.excilys.helper.CustomException;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.Pagination;

public class CompanyServiceTest {
	
	private static Logger log = LoggerFactory.getLogger(CompanyServiceTest.class);
	
	@Mock
	CompanyDAO companyDao;
	
	@InjectMocks
	CompanyService companyService;
	
	private static Company trueCompany;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		trueCompany = new Company.CompanyBuilder().setId(1).setName("Apple Inc.").build();
	}
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testFindCompany() {
		int fakeCompanyId = 1242354;
		String fakeCompanyName = "fake name";
		
		// find company by id
		assertThat(companyService.findById(trueCompany.getId()), is(trueCompany));

		exceptionRule.expect(CustomException.class);
	    exceptionRule.expectMessage(fakeCompanyId + Constant.TEXT_ER_NOT_FOUND);
		companyService.findById(fakeCompanyId);
		
		// find company by name
		assertThat(companyService.findByName(trueCompany.getName()), is(trueCompany));
		
		exceptionRule.expectMessage(fakeCompanyName + Constant.TEXT_ER_NOT_FOUND);
		companyService.findByName(fakeCompanyName);
	}
	
	@Test
	public void testGetListCompanies() {
		List<Company> companyList = companyService.getListCompanies();
		assertFalse(companyList.isEmpty());
	}
	
	@Test
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
