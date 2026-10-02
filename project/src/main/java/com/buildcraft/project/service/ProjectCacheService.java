package com.buildcraft.project.service;

import com.buildcraft.project.dto.ProjectCacheDto;
import com.buildcraft.project.entity.ConstructionProject;
import com.buildcraft.project.enums.ProjectStatus;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class ProjectCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ProjectCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ProjectCacheDto updateProjectCache(ConstructionProject constructionProject) {
        Long projectId=constructionProject.getId();
        String key = "project:" + projectId;
        redisTemplate.delete(key);
        HashOperations<String, Object, Object> hashOperations =
                redisTemplate.opsForHash();

        hashOperations.put(
                key,
                "managerId",
                String.valueOf(constructionProject.getManagerId())
        );

        hashOperations.put(
                key,
                "clientId",
                String.valueOf(constructionProject.getClientId())
        );

        hashOperations.put(
                key,
                "status",
                constructionProject.getStatus().name()
        );

        hashOperations.put(
                key,
                "title",
                constructionProject.getTitle()
        );

        hashOperations.put(
                key,
                "startDate",
                constructionProject.getStartDate().toString()
        );

        hashOperations.put(
                key,
                "endDate",
                constructionProject.getEndDate().toString()
        );
        ProjectCacheDto projectCacheDto = new ProjectCacheDto();

        projectCacheDto.setManagerId(
                constructionProject.getManagerId()
        );

        projectCacheDto.setClientId(
                constructionProject.getClientId()
        );

        projectCacheDto.setTitle(
                constructionProject.getTitle()
        );

        projectCacheDto.setStatus(
                constructionProject.getStatus()
        );

        projectCacheDto.setStartDate(
                constructionProject.getStartDate()
        );

        projectCacheDto.setEndDate(
                constructionProject.getEndDate()
        );

        return projectCacheDto;
    }

    public ProjectCacheDto getProject(Long projectId) {

        String key = "project:" + projectId;

        HashOperations<String, Object, Object> hashOperations =
                redisTemplate.opsForHash();

        Map<Object, Object> entries = hashOperations.entries(key);

        // Cache MISS
        if (entries.isEmpty()) {
            return null;
        }

        // Cache HIT
        ProjectCacheDto projectCacheDto = new ProjectCacheDto();

        projectCacheDto.setManagerId(
                Long.valueOf((String) entries.get("managerId"))
        );

        projectCacheDto.setClientId(
                Long.valueOf((String) entries.get("clientId"))
        );

        projectCacheDto.setTitle(
                (String) entries.get("title")
        );

        projectCacheDto.setStatus(
                ProjectStatus.valueOf(
                        (String) entries.get("status")
                )
        );

        projectCacheDto.setStartDate(
                LocalDate.parse(
                        (String) entries.get("startDate")
                )
        );

        projectCacheDto.setEndDate(
                LocalDate.parse(
                        (String) entries.get("endDate")
                )
        );

        return projectCacheDto;
    }

    public void deleteProjectCache(Long projectId) {

        String key = "project:" + projectId;

        redisTemplate.delete(key);
    }
}