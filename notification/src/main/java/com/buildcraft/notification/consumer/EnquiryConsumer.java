package com.buildcraft.notification.consumer;

import com.buildcraft.notification.dto.InquiryRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnquiryConsumer {

    private final JavaMailSender mailSender;

    public EnquiryConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // 👈 This annotation automatically spins up an internal loop listening to Kafka!
    @KafkaListener(topics = "enquiry-topic", groupId = "notification-group")
    public void consumeEnquiryEvent(InquiryRequest inquiry) {
        System.out.println("Kafka Event intercepted! Processing email dispatch for customer: " + inquiry.getCustomerName());
        try{
        // Put your existing email logic right here!
        SimpleMailMessage adminMessage = new SimpleMailMessage();
        adminMessage.setTo("kanugulacharan@gmail.com");
        adminMessage.setSubject("🚨 New Construction Enquiry Received!");
        adminMessage.setText("Hello Admin,\n\nA new customer enquiry has been logged:\n\n" +
                "Name: " + inquiry.getCustomerName() + "\n" +
                "Phone: " + inquiry.getPhoneNumber() + "\n" +
                "Email: " + inquiry.getEmailAddress() + "\n" +
                "Service: " + inquiry.getSelectedService() + "\n" +
                "Description: " + inquiry.getProjectDescription() + "\n\n" +
                "Check the Excel sheet and system backend dashboards for full logs.");
        mailSender.send(adminMessage);
    } catch (Exception e) {
        System.err.println("Admin Mail transmission pipeline exception: " + e.getMessage());
    }

    // Email #2: Auto-reply to Customer
        try {
        if (inquiry.getEmailAddress() != null && !inquiry.getEmailAddress().isBlank()) {
            SimpleMailMessage customerMessage = new SimpleMailMessage();
            customerMessage.setTo(inquiry.getEmailAddress());
            customerMessage.setSubject("Thank you for contacting BuildCraft");
            customerMessage.setText("Dear " + inquiry.getCustomerName() + ",\n\n" +
                    "Thank you for reaching out to BuildCraft. Our representative will get back to you shortly regarding our " +
                    inquiry.getSelectedService() + " services.\n\nBest Regards,\nBuildCraft Team");
            mailSender.send(customerMessage);
        }
    } catch (Exception e) {
        System.err.println("Customer Email transmission pipeline exception: " + e.getMessage());
    }

        System.out.println("Notification-Service background thread completed execution processing loop.");
}
}