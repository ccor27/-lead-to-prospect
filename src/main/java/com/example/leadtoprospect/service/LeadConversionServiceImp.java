package com.example.leadtoprospect.service;

import com.example.leadtoprospect.exception.ExceptionSystem;
import com.example.leadtoprospect.model.Customer;
import com.example.leadtoprospect.model.Prospect;
import com.example.leadtoprospect.model.ResultOrException;
import com.example.leadtoprospect.repository.ProspectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class LeadConversionServiceImp implements ILeadConversion{
    @Autowired
    private IExternalSystem iExternalSystem;
    @Autowired
    private IInternalSystem iInternalSystem;
    @Autowired
    private ProspectRepository prospectRepository;
    @Override
    public boolean convertLeadToProspect(Customer customer) throws ExceptionSystem {

            isProspect(customer);
            Prospect prospect = new Prospect(
                    customer.getNationalId(),
                    customer.getBirthDate(),
                    customer.getName(),
                    customer.getLastName(),
                    customer.getEmail()
            );
            prospectRepository.save(prospect);
            return true;

    }
    private Boolean isProspect(Customer customer) throws ExceptionSystem {
        CompletableFuture<ResultOrException<Boolean>> validCustomerInfo = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Boolean result = iExternalSystem.findAndCompareCustomer(customer.getNationalId());
                        return ResultOrException.ofResult(Optional.of(result));
                    } catch (ExceptionSystem e) {
                        return ResultOrException.ofException(Optional.of(e));
                    }

                });
        CompletableFuture<ResultOrException<Boolean>> hasJudicialRecord = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Boolean result = iExternalSystem.hasJudicialRecord(customer.getNationalId());
                        return ResultOrException.ofResult(Optional.of(result));
                    } catch (ExceptionSystem e) {
                        return ResultOrException.ofException(Optional.of(e));
                    }
                });
        CompletableFuture<ResultOrException<Boolean>> combinedResult = validCustomerInfo
                .thenCombine(hasJudicialRecord, (validInfoResult, hasJudicialResult) -> {
                    if (!validInfoResult.getException().isEmpty()) {
                        System.out.println(validInfoResult.getException().get());
                        return ResultOrException.ofException(validInfoResult.getException());
                    } else if (!hasJudicialResult.getException().isEmpty()) {
                        System.out.println(hasJudicialResult.getException().get());
                        return ResultOrException.ofException(hasJudicialResult.getException());
                    } else {
                        boolean success = validInfoResult.getResult().get() && !hasJudicialResult.getResult().get();
                        return ResultOrException.ofResult(Optional.of(success));
                    }
                });

        ResultOrException resultOrException = combinedResult.join();

        if (!resultOrException.getResult().isEmpty()) {//There is not an exception,there is a boolean, validate the score
            if ((Boolean) resultOrException.getResult().get()) {
                if (iInternalSystem.calculateScore(customer) > 60) {
                    return Boolean.TRUE;
                } else {
                    throw new ExceptionSystem("Insufficient score");
                }
            }

        }
        throw (ExceptionSystem) resultOrException.getException().get();
    }
}



