package com.buildcraft.project.service;

import com.buildcraft.project.dto.AssignClientRequest;
import com.buildcraft.project.dto.AssignManagerRequest;
import com.buildcraft.project.dto.CreateProjectRequest;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.enums.ProjectStatus;
import com.buildcraft.project.repository.ConstructionProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ConstructionProjectService {
    private final ConstructionProjectRepository constructionProjectRepository;
    private final ProjectCacheService projectCacheService;
    public ConstructionProjectService(ConstructionProjectRepository constructionProjectRepository, ProjectCacheService projectCacheService){
        this.constructionProjectRepository=constructionProjectRepository;
        this.projectCacheService = projectCacheService;
    }
    public ConstructionProject createProject(CreateProjectRequest createProjectRequest){
        ConstructionProject constructionProject=new ConstructionProject();

        constructionProject.setTitle(createProjectRequest.getTitle());

        constructionProject.setStartDate(createProjectRequest.getStartDate());
        constructionProject.setEndDate(createProjectRequest.getEndDate());

        constructionProject.setStatus(ProjectStatus.PLANNING);

        ConstructionProject saved =
                constructionProjectRepository.save(constructionProject);

        projectCacheService.updateProjectCache(saved);

        return saved;
    }
    public ConstructionProject assignManager(AssignManagerRequest assignManagerRequest,Long projectId){
        ConstructionProject constructionProject =
                constructionProjectRepository.findById(projectId)
                        .orElseThrow(() -> new RuntimeException("Project Not Found"));
        constructionProject.setManagerId(assignManagerRequest.getManagerId());
        constructionProject.setManagerId(assignManagerRequest.getManagerId());

        ConstructionProject updated =
                constructionProjectRepository.save(constructionProject);

        projectCacheService.updateProjectCache(updated);

        return updated;
    }
    public ConstructionProject assignClient(AssignClientRequest assignClientRequest,Long projectId){
        ConstructionProject constructionProject =
                constructionProjectRepository.findById(projectId)
                        .orElseThrow(() -> new RuntimeException("Project Not Found"));
        constructionProject.setClientId(assignClientRequest.getClientId());

        ConstructionProject updated =
                constructionProjectRepository.save(constructionProject);

        projectCacheService.updateProjectCache(updated);

        return updated;
    }
    public ConstructionProject getProject(Long projectId){
        ConstructionProject constructionProject =
                constructionProjectRepository.findById(projectId)
                        .orElseThrow(() -> new RuntimeException("Project Not Found"));
        return constructionProject;
    }
}
