package serviceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.dao.CompanyDAO;
import com.excilys.dao.ComputerDAO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.service.Pagination;
import com.excilys.validator.CustomException;

public class ComputerServiceTest {

    private static Logger log = Logger.getLogger(ComputerServiceTest.class);

    @Mock
    ComputerDAO computerDao;

    @Mock
    CompanyDAO companyDao;

    @InjectMocks
    ComputerService computerService;

    private Computer trueComputer;
    private Company trueCompany;

    private static final int FAKE_COMPUTER_ID = 1242354;
    private static final int TOTAL_COMPUTER = 574;
    private static final int FIND_COMPUTER_BY_ID = 1;
    private static final int LIMIT_PER_PAGE = 3;
    private static final int PAGE_NUMBER = 1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        computerService.setComputerInstance(computerDao);
        computerService.setCompanyInstance(companyDao);
        trueCompany = new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build();
        trueComputer = new Computer.ComputerBuilder().withId(1).withName("MacBook Pro 15.4 inch")
                .withCompany(trueCompany).build();
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFindComputerByIdShouldReturnComputer() {
        when(computerDao.findById(FIND_COMPUTER_BY_ID)).thenReturn(Optional.of(trueComputer));
        Assert.assertEquals(computerService.findById(FIND_COMPUTER_BY_ID), trueComputer);
        verify(computerDao).findById(FIND_COMPUTER_BY_ID);
    }

    @Test
    public void testFindComputerByIdShouldReturnNull() {
        when(computerDao.findById(FAKE_COMPUTER_ID)).thenReturn(Optional.ofNullable(null));
        Assert.assertNull(computerService.findById(FAKE_COMPUTER_ID));
        verify(computerDao).findById(FAKE_COMPUTER_ID);
    }

    @Test
    public void testGetListComputersShouldReturnNotEmptyComputerList() {
        List<Computer> computers = new ArrayList<>(Arrays.asList(trueComputer, trueComputer));
        when(computerDao.getList()).thenReturn(computers);
        List<Computer> computerList = computerService.getListComputers();
        assertFalse(computerList.isEmpty());
        verify(computerDao).getList();
    }

    @Test
    public void testGetListComputersPerPageShouldReturnComputerListPerPage() {
        Pagination pagination = new Pagination(LIMIT_PER_PAGE, PAGE_NUMBER, TOTAL_COMPUTER);
        List<Computer> computers = new ArrayList<>(Arrays.asList(trueComputer, trueComputer, trueComputer));

        when(computerDao.getListPerPage(pagination)).thenReturn(computers);
        List<Computer> firstPage = computerService.getListPerPage(pagination);
        verify(computerDao).getListPerPage(pagination);
        assertTrue(firstPage.size() == pagination.getLimit());
        assertTrue(firstPage.get(0).getId() == 1);
    }

    @Test
    public void testCreateComputerShouldReturnComputerCreated() {
        Computer mockComputer = new Computer.ComputerBuilder().withName("mock computer").build();
        when(computerDao.create(mockComputer)).thenReturn(mockComputer);
        assertThat(computerService.create(mockComputer), is(notNullValue()));
        verify(computerDao).create(mockComputer);
    }

    @Test(expected = CustomException.class)
    public void testCreateComputerShouldThrowExeption() {
        Computer mockComputer = new Computer.ComputerBuilder().withName("mock computer").withCompany(trueCompany)
                .build();
        int fakeCompanyId = 1234314;
        Company fakeCompany = new Company.CompanyBuilder().withId(fakeCompanyId).build();
        Computer mockComputer1 = new Computer.ComputerBuilder().withName("mock computer").withCompany(fakeCompany)
                .build();
        when(computerDao.create(mockComputer)).thenThrow(CustomException.class);
        computerService.create(mockComputer1);
        verify(computerDao).create(mockComputer1);
    }

    @Test
    public void testUpdateComputerShouldReturnComputerUpdated() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerDao.update(mockComputer)).thenReturn(mockComputer);
        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.of(mockComputer));
        assertThat(computerService.update(mockComputer), is(mockComputer));
        verify(computerDao).update(mockComputer);
        verify(computerDao).findById(mockComputer.getId());
    }

    @Test
    public void testUpdateComputerShouldThrowNotExistComputerException() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerDao.update(mockComputer)).thenReturn(mockComputer);

        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage("Computer does not exist in our database");
        computerService.update(mockComputer);
        verify(computerDao).update(mockComputer);
        verify(computerDao).findById(mockComputer.getId());

    }

    @Test
    public void testUpdateComputerShouldThrowNotNullDateException() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerDao.update(mockComputer)).thenReturn(mockComputer);

        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.of(mockComputer));

        mockComputer.setDiscontinued(LocalDate.of(2000, 1, 1));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage("introduced date is null while discontinued date is not null");
        computerService.update(mockComputer);
        verify(computerDao).update(mockComputer);
        verify(computerDao).findById(mockComputer.getId());
    }

    @Test
    public void testUpdateComputerShouldThrowSmallerDateException() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerDao.update(mockComputer)).thenReturn(mockComputer);
        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.of(mockComputer));

        mockComputer.setIntroduced(LocalDate.now());
        mockComputer.setDiscontinued(LocalDate.of(2000, 1, 1));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage("discontinued date is smaller then introduced date");
        computerService.update(mockComputer);
        verify(computerDao).update(mockComputer);
        verify(computerDao).findById(mockComputer.getId());
    }

    @Test
    public void testDeleteComputerShouldReturnTrue() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");

        when(computerDao.delete(mockComputer.getId())).thenReturn(true);
        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.ofNullable(mockComputer));
        assertTrue(computerService.remove(mockComputer));
        verify(computerDao).delete(mockComputer.getId());
    }

    @Test
    public void testDeleteComputerShouldThrowException() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");

        when(computerDao.delete(mockComputer.getId())).thenReturn(true);
        when(computerDao.findById(mockComputer.getId())).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage("Computer does not exist in our database");
        computerService.remove(mockComputer);
        verify(computerDao).delete(mockComputer.getId());
    }

}
