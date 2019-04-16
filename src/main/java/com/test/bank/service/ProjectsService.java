package com.test.bank.service;

import com.test.bank.dto.ProjectDTO;
import com.test.bank.mapper.ProjectMapper;
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
    private final ProjectMapper projectMapper;

    public Long addProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toProject(projectDTO);
        return projectsRepository.save(project).getId();
    }

    public Iterable<Project> getAllProjects() {
        return projectsRepository.findAll();
    }

    public Optional<Project> findProjectById(Long id) {
        return projectsRepository.findById(id);
    }

    public Optional<Project> getProjectByName(String name) {
        return projectsRepository.findByName(name);
    }

    public boolean deleteProject(Long id) {
        Project project = projectsRepository.findById(id).get();
        projectsRepository.delete(project);
        return true;
    }
}
