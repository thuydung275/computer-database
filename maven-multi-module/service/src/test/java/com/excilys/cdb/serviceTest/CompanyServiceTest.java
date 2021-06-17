package com.excilys.cdb.serviceTest;

import static org.junit.Assert.assertFalse;
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

import com.excilys.cdb.model.Company;
import com.excilys.cdb.repository.CompanyRepository;
import com.excilys.cdb.service.CompanyService;

public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    private Company trueCompany;
    private static final int FAKE_COMPANY_ID = 1242354;
    private static final int FIND_COMPANY_BY_ID = 1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        companyService = new CompanyService(companyRepository);
        trueCompany = new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build();
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFindCompanyByIdShouldReturnCompany() {
        when(companyRepository.findById(FIND_COMPANY_BY_ID)).thenReturn(Optional.of(trueCompany));
        Assert.assertEquals(companyService.findById(FIND_COMPANY_BY_ID), trueCompany);
        verify(companyRepository).findById(FIND_COMPANY_BY_ID);
    }

    @Test
    public void testFindCompanyByIdShouldReturnNull() {
        when(companyRepository.findById(FAKE_COMPANY_ID)).thenReturn(Optional.ofNullable(null));
        Assert.assertNull(companyService.findById(FAKE_COMPANY_ID));
        verify(companyRepository).findById(FAKE_COMPANY_ID);
    }

    @Test
    public void testGetListShouldReturnNotEmptyCompanyList() {
        List<Company> companies = new ArrayList<>(Arrays.asList(trueCompany, trueCompany));
        when(companyRepository.findAll()).thenReturn(companies);
        List<Company> companyList = companyService.getList();
        assertFalse(companyList.isEmpty());
        verify(companyRepository).findAll();
    }
}
