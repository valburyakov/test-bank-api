package com.test.bank.service;

import com.test.bank.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class ProjectsService {

    public long add(Project project) {
        return 1L;
    }

    public List<Project> getAllProjects() {
        Project project = new Project();
        project.setId(2);
        project.setName("Test project");

        Project project2 = new Project();
        project.setId(3);
        project.setName("Demo project");

        return asList(project, project2);
    }
}
