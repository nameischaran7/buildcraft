package com.buildcraft.portfolio;

import com.buildcraft.portfolio.entity.Project;
import com.buildcraft.portfolio.repository.ProjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProjectRepository repository;

    public DataInitializer(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            Project sample = new Project();
            sample.setImgClass("c1");
            sample.setIconClass("ti-building-skyscraper");
            sample.setIconColor("#185FA5");
            sample.setProjectType("Commercial");
            sample.setTitle("Greenfield Office Complex");
            sample.setDescription("5-storey commercial office block, Hyderabad");
            sample.setProjectYear("2024");
            sample.setCostBudget("₹4.2 Cr");
            repository.save(sample);
            System.out.println("Sample portfolio project seeded successfully!");
        }
    }
}