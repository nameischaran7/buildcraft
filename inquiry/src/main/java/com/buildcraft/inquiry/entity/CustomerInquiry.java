package com.buildcraft.inquiry.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_enquiries")
@Data
public class CustomerInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private  String emailAddress;

    private String phoneNumber;

    private String selectedService;

    @Column(columnDefinition = "TEXT")
    private String projectDescription;

    private LocalDateTime submittedAt;

    @PrePersist
    protected void onCreate(){
        this.submittedAt=LocalDateTime.now();
    }

}
