package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import com.example.leadtoprospect.model.Customer;
import com.example.leadtoprospect.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class InternalSystemServiceImp implements IInternalSystem{
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer findCustomer(Long id) throws ExceptionSystem {
        Customer customer = customerRepository.findById(id).orElseThrow(
                ()-> new ExceptionSystem("Customer doesn't exist in the internal system"));
        return customer;
    }

    @Override
    public int calculateScore(Customer customer) {
        if(customer==null)
            throw new RuntimeException("Is needed a customer to calculate the score");

        Random random = new Random();
        return random.nextInt(101);
    }
}
