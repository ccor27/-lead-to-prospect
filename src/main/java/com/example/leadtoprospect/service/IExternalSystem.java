package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;

public interface IExternalSystem {
    public Boolean findAndCompareCustomer(Long customerNationalId) throws ExceptionSystem;
    public Boolean hasJudicialRecord(Long customerNationalId) throws ExceptionSystem;
}
