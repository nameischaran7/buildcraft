package com.buildcraft.portfolio.entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String projectType;
    private String location;
    private String projectYear;
    private String costBudget;
    private String description;
    //UI
    private String imgClass;
    private String iconClass;
    private String iconColor;
}
