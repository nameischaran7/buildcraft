package com.buildcraft.notification.controller;

import com.buildcraft.notification.dto.InquiryRequest;
import com.buildcraft.notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // Injecting our asynchronous background service layer cleanly
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send-enquiry-email")
    public ResponseEntity<String> triggerEmails(@RequestBody InquiryRequest inquiry) {
        // Hand off payload to background threads (fires immediately in < 5ms)
        notificationService.sendEnquiryEmailsAsync(inquiry);

        // Instantly reply with an ACCEPTED state so OpenFeign client unblocks immediately
        return new ResponseEntity<>("Notification pipeline processing initiated successfully", HttpStatus.ACCEPTED);
    }
}