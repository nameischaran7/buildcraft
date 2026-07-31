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
    public ConstructionProjectService(ConstructionProjectRepository constructionProjectRepository){
        this.constructionProjectRepository=constructionProjectRepository;
    }
    public ConstructionProject createProject(CreateProjectRequest createProjectRequest){
        ConstructionProject constructionProject=new ConstructionProject();

        constructionProject.setTitle(createProjectRequest.getTitle());

        constructionProject.setStartDate(createProjectRequest.getStartDate());
        constructionProject.setEndDate(createProjectRequest.getEndDate());

        constructionProject.setStatus(ProjectStatus.PLANNING);

        return  constructionProjectRepository.save(constructionProject);
    }
    public ConstructionProject assignManager(AssignManagerRequest assignManagerRequest,Long projectId){

        ConstructionProject constructionProject =
                constructionProjectRepository.findById(projectId)
                        .orElseThrow(() -> new RuntimeException("Project Not Found"));

        constructionProject.setManagerId(assignManagerRequest.getManagerId());

        return constructionProjectRepository.save(constructionProject);

    }

    public ConstructionProject assignClient(AssignClientRequest assignClientRequest,Long projectId){

        ConstructionProject constructionProject =
                constructionProjectRepository.findById(projectId)
                        .orElseThrow(() -> new RuntimeException("Project Not Found"));

        constructionProject.setClientId(assignClientRequest.getClientId());

        return constructionProjectRepository.save(constructionProject);
    }
}
