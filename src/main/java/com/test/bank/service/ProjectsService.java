package com.test.bank.service;

import com.test.bank.model.Project;
import com.test.bank.repository.ProjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectsService {

    private final ProjectsRepository projectsRepository;

    public Long addProject(Project project) {
        return projectsRepository.save(project).getId();
    }

    public Iterable<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id) {
        return projectsRepository.findById(id);
    }

    public boolean deleteProject(Long id) {
        Optional<Project> value = findProjectById(id);
        if (!value.isPresent()) {
            return false;
        }

        Project project = value.get();
        project.setDeleted(true);
        addProject(project);

        return true;
    }
}
