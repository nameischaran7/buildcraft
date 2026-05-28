package com.buildcraft.inquiry.controller;

import com.buildcraft.inquiry.client.NotificationClient;
import com.buildcraft.inquiry.entity.CustomerInquiry;
import com.buildcraft.inquiry.repository.EnquiryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enquiries")

public class EnquiryController {
    private final EnquiryRepository enquiryRepository;
    private final NotificationClient notificationClient;
    public EnquiryController(EnquiryRepository enquiryRepository,NotificationClient notificationClient){
        this.enquiryRepository=enquiryRepository;
        this.notificationClient=notificationClient;
    }
    @PostMapping
    public ResponseEntity<CustomerInquiry>  submitEnquiry(@RequestBody CustomerInquiry inquiry){
        CustomerInquiry savedInquiry=enquiryRepository.save(inquiry);
        notificationClient.triggerMails(savedInquiry);
        return new ResponseEntity<>(savedInquiry, HttpStatus.CREATED);
    }
 }
