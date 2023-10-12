package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import com.example.leadtoprospect.model.Customer;
import com.example.leadtoprospect.model.Prospect;
import com.example.leadtoprospect.repository.ProspectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class LeadConversionServiceImpTest {

    @Autowired
    private LeadConversionServiceImp leadConversionService;

    @MockBean
    private IExternalSystem externalSystem;

    @MockBean
    private IInternalSystem internalSystem;

    @MockBean
    private ProspectRepository prospectRepository;


    @Test
    void convertLeadToProspectShouldSaveProspect() throws ExceptionSystem {
        // Arrange
        Customer customer = new Customer(78L,new Date(1999,01,12),"john","smith","john@gmail.com");
        Prospect prospect = new Prospect(78L,new Date(1999,01,12),"john","smith","john@gmail.com");
        when(externalSystem.findAndCompareCustomer(customer.getNationalId())).thenReturn(CompletableFuture.completedFuture(Boolean.TRUE).join());
        when(externalSystem.hasJudicialRecord(customer.getNationalId())).thenReturn(CompletableFuture.completedFuture(Boolean.FALSE).join());
        when(internalSystem.calculateScore(any(Customer.class))).thenReturn(70);
        when(prospectRepository.save(any(Prospect.class))).thenReturn(prospect);
        // Act
        boolean result = leadConversionService.convertLeadToProspect(customer);
        // Assert
        assertTrue(result);
        verify(prospectRepository,times(1)).save(any(Prospect.class));
    }

    @Test
    void convertLeadToProspectShouldNotSaveProspectCustomerNotExistInTheExternalSystem() throws ExceptionSystem {
        // Arrange
        Customer customer = new Customer(44L,new Date(1999,01,12),"john","smith","john@gmail.com"/* your customer details here */);
        when(externalSystem.findAndCompareCustomer(customer.getNationalId())).thenThrow(ExceptionSystem.class);
        when(externalSystem.hasJudicialRecord(customer.getNationalId())).thenReturn(CompletableFuture.completedFuture(Boolean.FALSE).join());
       when(internalSystem.calculateScore(customer)).thenAnswer(invocationOnMock -> 70);
        // Assert
        assertThrows(ExceptionSystem.class, () -> leadConversionService.convertLeadToProspect(customer));
    }
    @Test
    void convertLeadToProspectShouldNotSaveProspectCustomerHasJudicialRecord() throws ExceptionSystem {
        // Arrange
        Customer customer = new Customer(58L,new Date(1999,01,12),"john","smith","john@gmail.com"/* your customer details here */);
        when(externalSystem.findAndCompareCustomer(customer.getNationalId())).thenThrow(ExceptionSystem.class);
        when(externalSystem.hasJudicialRecord(customer.getNationalId())).thenReturn(CompletableFuture.completedFuture(Boolean.FALSE).join());
        when(internalSystem.calculateScore(customer)).thenAnswer(invocationOnMock -> 70);
        // Assert
        assertThrows(ExceptionSystem.class, () -> leadConversionService.convertLeadToProspect(customer));
    }
    @Test
    void convertLeadToProspectShouldNotSaveProspectCustomerNotExistInTheInternalSystem() throws ExceptionSystem {
        // Arrange
        Customer customer = new Customer(99L,new Date(1999,01,12),"john","smith","john@gmail.com"/* your customer details here */);
        when(externalSystem.findAndCompareCustomer(customer.getNationalId())).thenThrow(ExceptionSystem.class);
        when(externalSystem.hasJudicialRecord(customer.getNationalId())).thenReturn(CompletableFuture.completedFuture(Boolean.FALSE).join());
        when(internalSystem.calculateScore(customer)).thenAnswer(invocationOnMock -> 70);
        // Assert
        assertThrows(ExceptionSystem.class, () -> leadConversionService.convertLeadToProspect(customer));
    }

}