package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ExternalSystemServiceImpTest {
    @InjectMocks
    private ExternalSystemServiceImp externalSystemServiceImp;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAndCompareCustomerShouldSuccess() throws ExceptionSystem {
        assertTrue(externalSystemServiceImp.findAndCompareCustomer(78L));
        assertTrue(externalSystemServiceImp.findAndCompareCustomer(79L));
    }
    @Test
    void findAndCompareCustomerShouldFailDataCustomerNotMatch() throws ExceptionSystem {
        ExceptionSystem exceptionSystemNotMatch = assertThrows(ExceptionSystem.class,()->externalSystemServiceImp.findAndCompareCustomer(58L));
        assertEquals("The customer exist but the data doesn't match",exceptionSystemNotMatch.getMessage());
    }
    @Test
    void findAndCompareCustomerShouldFailCustomerNotExistInTheExternalSystem() throws ExceptionSystem {
        ExceptionSystem exceptionSystemNotMatch = assertThrows(ExceptionSystem.class,()->externalSystemServiceImp.findAndCompareCustomer(44L));
        assertEquals("The customer doesn't exist in the external system",exceptionSystemNotMatch.getMessage());
    }
    @Test
    void findAndCompareCustomerShouldFailCustomerNotExistInInternalSystem() throws ExceptionSystem {
        ExceptionSystem exceptionSystemNotMatch = assertThrows(ExceptionSystem.class,()->externalSystemServiceImp.findAndCompareCustomer(99L));
        assertEquals("The customer doesn't exist in the internal system",exceptionSystemNotMatch.getMessage());
    }



    @Test
    void hasNotJudicialRecord() throws ExceptionSystem {
        assertFalse(externalSystemServiceImp.hasJudicialRecord(78L));
        assertFalse(externalSystemServiceImp.hasJudicialRecord(58L));
    }
    @Test
    void hasJudicialRecord() throws ExceptionSystem {
        ExceptionSystem exceptionSystemNotMatch = assertThrows(ExceptionSystem.class,()->externalSystemServiceImp.hasJudicialRecord(79L));
        assertEquals("The customer has judicial record",exceptionSystemNotMatch.getMessage());
    }

}