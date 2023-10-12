package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import com.example.leadtoprospect.model.Customer;

public interface IInternalSystem {
    public Customer findCustomer(Long id) throws ExceptionSystem;
    public int calculateScore(Customer customer);
}
