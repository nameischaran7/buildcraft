package com.buildcraft.project.security;

import com.buildcraft.project.dto.ProjectCacheDto;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.repository.ConstructionProjectRepository;
import com.buildcraft.project.service.ProjectCacheService;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationService {
    private final ProjectCacheService projectCacheService;
    private final ConstructionProjectRepository constructionProjectRepository;
    public AuthorizationService(ProjectCacheService projectCacheService, ConstructionProjectRepository constructionProjectRepository) {
        this.projectCacheService = projectCacheService;
        this.constructionProjectRepository = constructionProjectRepository;
    }

    public void checkCanManageProject(Long userId,Long projectId){
        ProjectCacheDto projectCacheDto=projectCacheService.getProject(projectId);
        if(projectCacheDto==null)throw new RuntimeException("Project not found");
        if(!userId.equals(projectCacheDto.getManagerId()))throw new RuntimeException("User is not authorized to manage this project");
    }
    public ProjectCacheDto checkCanViewProject(Long userId, String  role, Long projectId){
        ProjectCacheDto projectCacheDto=projectCacheService.getProject(projectId);
        if(projectCacheDto==null){
            ConstructionProject constructionProject =
                    constructionProjectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project Not Found"));

            projectCacheDto=projectCacheService.updateProjectCache(constructionProject);
        }
        if("SUPER_ADMIN".equals(role))return projectCacheDto;
        if(!userId.equals(projectCacheDto.getClientId()) && !userId.equals(projectCacheDto.getManagerId()))throw new RuntimeException("User is not authorized to view this project");
        return projectCacheDto;
    }
}
