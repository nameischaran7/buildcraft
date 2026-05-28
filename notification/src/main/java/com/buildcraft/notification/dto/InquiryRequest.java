package com.buildcraft.notification.dto;

public class InquiryRequest {
    private String customerName;
    private String phoneNumber;
    private String emailAddress;
    private String selectedService;
    private String projectDescription;

    // Generate standard Getters and Setters here
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public String getSelectedService() { return selectedService; }
    public void setSelectedService(String selectedService) { this.selectedService = selectedService; }
    public String getProjectDescription() { return projectDescription; }
    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
}