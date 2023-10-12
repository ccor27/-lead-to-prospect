package com.example.leadtoprospect.service;

import com.example.leadtoprospect.model.Customer;

import java.util.Map;

public interface ILeadConversion {
    public boolean convertLeadToProspect(Customer customer) throws Exception;
}
