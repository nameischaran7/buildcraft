package com.buildcraft.project.repository;

import com.buildcraft.project.entity.ConstructionProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstructionProjectRepository extends JpaRepository<ConstructionProject,Long> {
    Optional<ConstructionProject> findById(Long Id);
}
