package com.buildcraft.project.dto;

import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectCacheDto {
    private Long managerId;
    private Long clientId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatus status;

}
