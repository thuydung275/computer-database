package serviceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.helper.Constant;
import com.excilys.helper.CustomException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.Pagination;

public class ComputerServiceTest {

	private static Logger log = Logger.getLogger(ComputerServiceTest.class);
	
	@Mock
	ComputerDAO computerDao;
	
	@Mock
	CompanyDAO companyDao;
	
	@InjectMocks
	ComputerService computerService;
	
	@InjectMocks
	CompanyService companyService;
	
	private static Computer trueComputer;
	private static Company trueCompany;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		trueCompany = new Company.CompanyBuilder().setId(1).setName("Apple Inc.").build();
		trueComputer = new Computer.ComputerBuilder().setId(1).setName("MacBook Pro 15.4 inch").setCompany(trueCompany).build();
	}
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testFindComputer() {
		assertThat(computerService.findById(trueComputer.getId()), is(trueComputer));
		
		int fakeComputerId = 1342334;
		exceptionRule.expect(CustomException.class);
	    exceptionRule.expectMessage(fakeComputerId + Constant.TEXT_ER_NOT_FOUND);
	    computerService.findById(fakeComputerId);
	}
	
	@Test
	public void testGetListComputers() {
		List<Computer> computerList = computerService.getListComputers();
		assertFalse(computerList.isEmpty());
	}
	
	@Test
	public void testGetListComputersPerPage() {
		List<Computer> computerList = computerService.getListComputers();
		Pagination pagination = new Pagination(computerList.size());
		
		List<Computer> firstPage = computerService.getListPerPage(pagination);
		assertTrue(firstPage.size() == pagination.getLimit());
		assertTrue(firstPage.get(0).getId() == 1);
		
		pagination.next();
		
		List<Computer> secondPage = computerService.getListPerPage(pagination);
		assertTrue(secondPage.get(0).getId() == pagination.getLimit() + 1);
	}
	
//	@Test(expected = CustomException.class)
//	public void testAddComputer() {
//		Computer mockComputer = new Computer.ComputerBuilder().setName("mock computer").setCompany(trueCompany).build();
//		Mockito.when(computerDao.create(mockComputer)).thenReturn(mockComputer);
//		assertThat(computerService.create(mockComputer), is(notNullValue()));
//		
//		int fakeCompanyId = 1234314;
//		Company fakeCompany = new Company.CompanyBuilder().setId(fakeCompanyId).build();
//		Computer mockComputer1 = new Computer.ComputerBuilder().setName("mock computer").setCompany(fakeCompany).build();
//		Mockito.when(computerDao.create(mockComputer)).thenThrow(CustomException.class);
//		computerService.create(mockComputer1);
//	}
	
//	@Test
//	public void testUpdateComputer() {
//		Computer mockComputer = Computer.copy(trueComputer);
//		mockComputer.setName("mock computer");
//		Mockito.when(computerDao.update(mockComputer)).thenReturn(mockComputer);
//		assertThat(computerService.update(mockComputer), is(mockComputer));
//	}
}
