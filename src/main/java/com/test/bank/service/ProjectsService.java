package com.test.bank.service;

import com.test.bank.model.Project;
import com.test.bank.repository.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectsService {

    @Autowired
    private ProjectsRepository projectsRepository;

    @Transactional
    public Long add(Project project) {
        return projectsRepository.save(project).getId();
    }

    @Transactional
    public Iterable<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    @Transactional
    public Optional<Project> findProjectById(Long id) {
        return projectsRepository.findById(id);
    }

    @Transactional
    public boolean deleteProject(Long id) {
        Optional<Project> value = findProjectById(id);
        if (!value.isPresent()) {
            return false;
        }

        Project project = value.get();
        project.setDeleted(true);
        add(project);

        return true;
    }
}
