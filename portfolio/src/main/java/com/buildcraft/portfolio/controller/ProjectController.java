package com.buildcraft.portfolio.controller;

import com.buildcraft.portfolio.entity.Project;
import com.buildcraft.portfolio.repository.ProjectRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")

public class ProjectController {
    private final ProjectRepository projectRepository;
    public ProjectController(ProjectRepository projectRepository){
        this.projectRepository=projectRepository;
    }
    @GetMapping
    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }
}
