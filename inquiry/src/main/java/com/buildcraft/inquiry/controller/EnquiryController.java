package com.buildcraft.inquiry.controller;

import com.buildcraft.inquiry.entity.CustomerInquiry;
import com.buildcraft.inquiry.repository.EnquiryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enquiries")

public class EnquiryController {
    private final EnquiryRepository enquiryRepository;
    public EnquiryController(EnquiryRepository enquiryRepository){
        this.enquiryRepository=enquiryRepository;
    }
    @PostMapping
    public ResponseEntity<CustomerInquiry>  submitEnquiry(@RequestBody CustomerInquiry inquiry){
        CustomerInquiry savedInquiry=enquiryRepository.save(inquiry);
        return new ResponseEntity<>(savedInquiry, HttpStatus.CREATED);
    }
 }
