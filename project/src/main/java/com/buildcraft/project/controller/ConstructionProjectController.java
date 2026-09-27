package com.buildcraft.project.controller;

import com.buildcraft.project.dto.AssignClientRequest;
import com.buildcraft.project.dto.AssignManagerRequest;
import com.buildcraft.project.dto.CreateProjectRequest;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.security.AuthorizationService;
import com.buildcraft.project.service.ConstructionProjectService;

import com.buildcraft.project.service.ProjectCacheService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;
    private final AuthorizationService authorizationService;

    public ConstructionProjectController(ConstructionProjectService constructionProjectService, AuthorizationService authorizationService){
        this.constructionProjectService=constructionProjectService;
        this.authorizationService = authorizationService;

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
    @GetMapping("/me")
    public Long getUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null)throw new RuntimeException();
        return (Long) authentication.getPrincipal();
    }
    @GetMapping("/{projectId}")
    public ConstructionProject getProject(@PathVariable Long projectId) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        Long userId=(Long)authentication.getPrincipal();
        String role=authentication.getAuthorities()
                        .iterator()
                .next()
                .getAuthority()
                .substring(5);
        System.out.println("USER ID = " + userId);
        System.out.println("ROLE = " + role);
        System.out.println("AUTHORITIES = " + authentication.getAuthorities());
        authorizationService.checkCanViewProject(userId,role,projectId);
        return constructionProjectService.getProject(projectId);
    }
}
