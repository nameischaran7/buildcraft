package com.buildcraft.inquiry.controller;

import com.buildcraft.inquiry.client.NotificationClient;
import com.buildcraft.inquiry.entity.CustomerInquiry;
import com.buildcraft.inquiry.repository.EnquiryRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/api/v1/enquiries")

public class EnquiryController {
    private final EnquiryRepository enquiryRepository;
    private final NotificationClient notificationClient;
    private static final String EXCEL_FILE_PATH="C:\\Users\\Charan Teja k\\OneDrive\\Desktop\\BuildCraft.xlsx";
    public EnquiryController(EnquiryRepository enquiryRepository,NotificationClient notificationClient){
        this.enquiryRepository=enquiryRepository;
        this.notificationClient=notificationClient;
    }
    @PostMapping
    public ResponseEntity<CustomerInquiry>  submitEnquiry(@RequestBody CustomerInquiry inquiry){
        CustomerInquiry savedInquiry=enquiryRepository.save(inquiry);
        notificationClient.triggerMails(savedInquiry);
        try {
            logToExcel(savedInquiry);
        } catch (Exception e) {
            System.err.println("Excel spreadsheet manipulation failure: " + e.getMessage());
        }

        // 4. Return response to frontend INSTANTLY without waiting for the emails to send!
        return new ResponseEntity<>(savedInquiry, HttpStatus.CREATED);
    }

    private synchronized void logToExcel(CustomerInquiry inquiry) throws Exception {
        Workbook workbook;
        Sheet sheet;
        File file = new File(EXCEL_FILE_PATH);

        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                workbook = WorkbookFactory.create(fileInputStream);
                sheet = workbook.getSheetAt(0);
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Customer Enquiries");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Enquiry ID", "Customer Name", "Phone Number", "Email Address", "Selected Service", "Project Description"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        }

        int nextRowIndex = sheet.getLastRowNum() + 1;
        Row dataRow = sheet.createRow(nextRowIndex);

        dataRow.createCell(0).setCellValue(inquiry.getId() != null ? inquiry.getId().toString() : "NEW");
        dataRow.createCell(1).setCellValue(inquiry.getCustomerName() != null ? inquiry.getCustomerName() : "");
        dataRow.createCell(2).setCellValue(inquiry.getPhoneNumber() != null ? inquiry.getPhoneNumber() : "");
        dataRow.createCell(3).setCellValue(inquiry.getEmailAddress() != null ? inquiry.getEmailAddress() : "");
        dataRow.createCell(4).setCellValue(inquiry.getSelectedService() != null ? inquiry.getSelectedService() : "");
        dataRow.createCell(5).setCellValue(inquiry.getProjectDescription() != null ? inquiry.getProjectDescription() : "");

        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }
 }
