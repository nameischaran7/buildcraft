package com.buildcraft.project.service;

import com.buildcraft.project.dto.ProjectCacheDto;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.enums.ProjectStatus;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProjectCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ProjectCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public void updateProjectCache(ConstructionProject constructionProject){
        String key="project:"+constructionProject.getId();
        HashOperations<String,Object,Object> hashOperations=redisTemplate.opsForHash();
        hashOperations.put(key,"managerId",constructionProject.getManagerId());
        hashOperations.put(key,"clientId",constructionProject.getClientId());
        hashOperations.put(key,"status",constructionProject.getStatus());
        hashOperations.put(key,"title",constructionProject.getTitle());
        hashOperations.put(key,"startDate",constructionProject.getStartDate().toString());
        hashOperations.put(key,"endDate",constructionProject.getEndDate().toString());
    }
    public ProjectCacheDto getProject(Long projectId){
        String key="project:"+projectId;
        if (!redisTemplate.hasKey(key))return null;
        ProjectCacheDto projectCacheDto=new ProjectCacheDto();
        HashOperations<String,Object,Object> hashOperations=redisTemplate.opsForHash();
        projectCacheDto.setManagerId((Long) hashOperations.get(key,"managerId"));
        projectCacheDto.setClientId((Long) hashOperations.get(key,"clientId"));
        projectCacheDto.setTitle((String) hashOperations.get(key,"title"));
        projectCacheDto.setStartDate(LocalDate.parse((String)hashOperations.get(key,"startDate")));
        projectCacheDto.setEndDate(LocalDate.parse((String)hashOperations.get(key,"endDate")));
        projectCacheDto.setStatus((ProjectStatus) hashOperations.get(key,"status"));
        return projectCacheDto;
    }
}