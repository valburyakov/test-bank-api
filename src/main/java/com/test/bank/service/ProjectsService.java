package com.test.bank.service;

import com.test.bank.model.Project;
import com.test.bank.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    public Long add(Project project) {
        return projectsRepository.save(project).getId();
    }

    public Iterable<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id) {
        return projectsRepository.findById(id);
    }
}
