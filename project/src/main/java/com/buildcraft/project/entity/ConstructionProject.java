package com.buildcraft.project.entity;
import com.buildcraft.project.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Table(name = "construction_projects")
@Data
public class ConstructionProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private Long managerId;
    private Long clientId;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
}
