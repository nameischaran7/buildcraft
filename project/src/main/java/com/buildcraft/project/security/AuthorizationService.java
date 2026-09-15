package com.buildcraft.project.security;

import com.buildcraft.project.dto.ProjectCacheDto;
import com.buildcraft.project.service.ProjectCacheService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final ProjectCacheService projectCacheService;

    public AuthorizationService(ProjectCacheService projectCacheService) {
        this.projectCacheService = projectCacheService;
    }

    public void checkCanManageProject(Long userId,Long projectId){
        ProjectCacheDto projectCacheDto=projectCacheService.getProject(projectId);
        if(projectCacheDto==null)throw new RuntimeException("Project not found");
        if(!userId.equals(projectCacheDto.getManagerId()))throw new RuntimeException("User is not authorized to manage this project");
    }
}
