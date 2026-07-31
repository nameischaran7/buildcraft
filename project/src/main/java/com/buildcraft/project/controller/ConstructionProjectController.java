package com.buildcraft.project.controller;

import com.buildcraft.project.dto.AssignClientRequest;
import com.buildcraft.project.dto.AssignManagerRequest;
import com.buildcraft.project.dto.CreateProjectRequest;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.service.ConstructionProjectService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ConstructionProjectController {


    private final ConstructionProjectService constructionProjectService;

    public ConstructionProjectController(ConstructionProjectService constructionProjectService){
        this.constructionProjectService=constructionProjectService;
    }

    @PostMapping
    public ConstructionProject createProject(@RequestBody  CreateProjectRequest createProjectRequest){
        return constructionProjectService.createProject(createProjectRequest);
    }

    @PutMapping("/{projectId}/manager")
    public ConstructionProject assignManager(@RequestBody AssignManagerRequest assignManagerRequest,@PathVariable Long projectId){
        return constructionProjectService.assignManager(assignManagerRequest,projectId);
    }

    @PutMapping("/{projectId}/client")
    public ConstructionProject assignClient(@RequestBody AssignClientRequest assignClientRequest,@PathVariable Long projectId){
        return constructionProjectService.assignClient(assignClientRequest,projectId);
    }
}
