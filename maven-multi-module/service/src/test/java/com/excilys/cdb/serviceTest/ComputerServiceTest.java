package com.excilys.cdb.serviceTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.repository.CompanyRepository;
import com.excilys.cdb.repository.ComputerRepository;
import com.excilys.cdb.service.ComputerService;

public class ComputerServiceTest {

    private static Logger log = Logger.getLogger(ComputerServiceTest.class);

    @Mock
    ComputerRepository computerRepository;

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    ComputerService computerService;

    private Computer trueComputer;
    private Company trueCompany;

    private static final int FAKE_COMPUTER_ID = 1242354;
    private static final int FIND_COMPUTER_BY_ID = 1;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        computerService = new ComputerService(computerRepository);
        trueCompany = new Company.CompanyBuilder().withId(1).withName("Apple Inc.").build();
        trueComputer = new Computer.ComputerBuilder().withId(1).withName("MacBook Pro 15.4 inch")
                .withCompany(trueCompany).build();
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testFindComputerByIdShouldReturnComputer() {
        when(computerRepository.findById(FIND_COMPUTER_BY_ID)).thenReturn(Optional.of(trueComputer));
        Assert.assertEquals(computerService.findById(FIND_COMPUTER_BY_ID), trueComputer);
        verify(computerRepository).findById(FIND_COMPUTER_BY_ID);
    }

    @Test
    public void testFindComputerByIdShouldReturnNull() {
        when(computerRepository.findById(FAKE_COMPUTER_ID)).thenReturn(Optional.ofNullable(null));
        Assert.assertNull(computerService.findById(FAKE_COMPUTER_ID));
        verify(computerRepository).findById(FAKE_COMPUTER_ID);
    }

    @Test
    public void testGetListShouldReturnNotEmptyComputerList() {
        List<Computer> computers = new ArrayList<>(Arrays.asList(trueComputer, trueComputer));
        when(computerRepository.findAll()).thenReturn(computers);
        List<Computer> computerList = computerService.getList();
        assertFalse(computerList.isEmpty());
        verify(computerRepository).findAll();
    }

    @Test
    public void testCreateComputerShouldReturnComputer() {
        Computer mockComputer = new Computer.ComputerBuilder().withName("mock computer").build();
        when(computerRepository.create(mockComputer)).thenReturn(mockComputer);
        assertNotNull(computerService.create(mockComputer));
        verify(computerRepository).create(mockComputer);
    }

    @Test
    public void testUpdateComputerShouldReturnComputer() throws Exception {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerRepository.update(mockComputer)).thenReturn(mockComputer);
        when(computerRepository.findById(mockComputer.getId())).thenReturn(Optional.ofNullable(mockComputer));
        assertNotNull(computerService.update(mockComputer));
        verify(computerRepository).update(mockComputer);
    }

    @Test
    public void testUpdateComputerShouldThrowNotExistComputerException() throws Exception {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerRepository.update(mockComputer)).thenReturn(mockComputer);
        when(computerRepository.findById(mockComputer.getId())).thenReturn(Optional.ofNullable(null));
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Computer does not exist in our database");
        computerService.update(mockComputer);
        verify(computerRepository).update(mockComputer);
        verify(computerRepository).findById(mockComputer.getId());

    }

    @Test
    public void testDeleteComputerShouldReturnTrue() {
        Computer mockComputer = Computer.copy(trueComputer);
        mockComputer.setName("mock computer");
        when(computerRepository.delete(mockComputer.getId())).thenReturn(true);
        assertTrue(computerService.delete(mockComputer.getId()));
        verify(computerRepository).delete(mockComputer.getId());
    }
}
