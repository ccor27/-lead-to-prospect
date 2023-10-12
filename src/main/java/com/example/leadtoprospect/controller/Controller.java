package com.example.leadtoprospect.controller;

import com.example.leadtoprospect.model.Customer;
import com.example.leadtoprospect.service.IInternalSystem;
import com.example.leadtoprospect.service.ILeadConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/lead")
public class Controller {
    @Autowired
    private ILeadConversion iLeadConversion;
    @Autowired
    private IInternalSystem iInternalSystem;
    @GetMapping("/convert/{id}")
    public ResponseEntity<String> convert(@PathVariable Long id){
        try {
        Customer customer = iInternalSystem.findCustomer(id);
        iLeadConversion.convertLeadToProspect(customer);
        return new ResponseEntity<>("conversion successfully",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("conversion failed: "+e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
