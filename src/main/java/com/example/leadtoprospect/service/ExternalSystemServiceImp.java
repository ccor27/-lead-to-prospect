package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import org.springframework.stereotype.Service;

@Service
public class ExternalSystemServiceImp implements IExternalSystem{

    private Long existMatchHasNotJudicialRecord = 78L;
    private Long existMatchHasJudicialRecord=79L;
    private Long existNotMatchHasNotJudicialRecord=58L;
    private Long notExistInTheExternalSystem=44L;

    @Override
    public Boolean findAndCompareCustomer(Long customerNationalId) throws ExceptionSystem {
       if(customerNationalId.equals(existMatchHasNotJudicialRecord)){
            return Boolean.TRUE;
        }else if(customerNationalId.equals(existMatchHasJudicialRecord)){
            return Boolean.TRUE;
        } else if(customerNationalId.equals(existNotMatchHasNotJudicialRecord)){
            throw new ExceptionSystem("The customer exist but the data doesn't match");
        }else if(customerNationalId.equals(notExistInTheExternalSystem) ){
            throw new ExceptionSystem("The customer doesn't exist in the external system");
       }else{
            throw new ExceptionSystem("The customer doesn't exist in the internal system");
        }
    }
    @Override
    public Boolean hasJudicialRecord(Long customerNationalId) throws ExceptionSystem {
        if(customerNationalId == existMatchHasNotJudicialRecord || customerNationalId == existNotMatchHasNotJudicialRecord){// return false
            return Boolean.FALSE;
        }else{
            throw new ExceptionSystem("The customer has judicial record");
        }
    }
}
