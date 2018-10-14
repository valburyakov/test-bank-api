package com.test.bank.service;

import com.test.bank.model.Project;
import com.test.bank.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    public Project add(Project project) {
        return projectsRepository.save(project);
    }

    public Iterable<Project> getAllProjects() {
        return projectsRepository.findAll();
    }
}
