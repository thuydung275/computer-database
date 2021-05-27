package serviceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.excilys.dao.CompanyDAO;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;
import com.excilys.service.Pagination;
import com.excilys.validator.CustomException;

public class CompanyServiceTest {

    @Mock
    CompanyDAO companyDao;

    @InjectMocks
    CompanyService companyService;

    private Company trueCompany;
    private static final int FAKE_COMPANY_ID = 1242354;
    private static final String FAKE_COMPANY_NAME = "fake name";
    private static final int TOTAL_COMPANY = 42;
    private static final String FIND_COMPANY_BY_NAME = "Apple Inc.";
    private static final int FIND_COMPANY_BY_ID = 1;
    private static final int LIMIT_PER_PAGE = 3;
    private static final int PAGE_NUMBER = 1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        companyService.setCompanyInstance(companyDao);
        trueCompany = new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build();
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFindCompanyByIdShouldReturnCompany() {
        when(companyDao.findById(FIND_COMPANY_BY_ID)).thenReturn(Optional.of(trueCompany));
        Assert.assertEquals(companyService.findById(FIND_COMPANY_BY_ID), trueCompany);
        verify(companyDao).findById(FIND_COMPANY_BY_ID);
    }

    @Test
    public void testFindCompanyByIdShouldThrowException() {
        when(companyDao.findById(FAKE_COMPANY_ID)).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage(FAKE_COMPANY_ID + CustomException.TEXT_ER_NOT_FOUND);
        companyService.findById(FAKE_COMPANY_ID);
        verify(companyDao).findById(FAKE_COMPANY_ID);
    }

    @Test
    public void findCompanyByNameShouldReturnCompany() {
        when(companyDao.findByName(FIND_COMPANY_BY_NAME)).thenReturn(Optional.of(trueCompany));
        Assert.assertEquals(companyService.findByName(FIND_COMPANY_BY_NAME), trueCompany);
        verify(companyDao).findByName(FIND_COMPANY_BY_NAME);
    }

    @Test
    public void findCompanyByNameShouldThrowException() {
        when(companyDao.findByName(FAKE_COMPANY_NAME)).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(CustomException.class);
        exceptionRule.expectMessage(FAKE_COMPANY_NAME + CustomException.TEXT_ER_NOT_FOUND);
        companyService.findByName(FAKE_COMPANY_NAME);
        verify(companyDao).findByName(FAKE_COMPANY_NAME);
    }

    @Test
    public void testGetListCompaniesShouldReturnNotEmptyCompanyList() {
        List<Company> companies = new ArrayList<>(Arrays.asList(trueCompany, trueCompany));
        when(companyDao.getList()).thenReturn(companies);
        List<Company> companyList = companyService.getListCompanies();
        assertFalse(companyList.isEmpty());
        verify(companyDao).getList();
    }

    @Test
    public void testGetListCompaniesPerPageCompanyListPerPage() {
        Pagination pagination = new Pagination(LIMIT_PER_PAGE, PAGE_NUMBER, TOTAL_COMPANY);
        List<Company> companies = new ArrayList<>(Arrays.asList(trueCompany, trueCompany, trueCompany));

        when(companyDao.getListPerPage(pagination)).thenReturn(companies);
        List<Company> firstPage = companyService.getListPerPage(pagination);
        verify(companyDao).getListPerPage(pagination);
        assertTrue(firstPage.size() == pagination.getLimit());
        assertTrue(firstPage.get(0).getId() == 1);
    }
}
