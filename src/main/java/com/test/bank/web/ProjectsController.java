package com.test.bank.web;

import com.test.bank.model.Project;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
public class ProjectsController {

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public Project createProject(@RequestBody Project project) {
        project.setId(1);
        return project;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> getAllProjects() {
        Project project = new Project();
        project.setId(2);
        project.setName("Test project");
        return asList(project);
    }


}
