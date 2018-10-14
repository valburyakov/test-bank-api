package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@RestController
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public Map<String, Long> createProject(@RequestBody Project project) {
        long id = projectsService.add(project);
        return Collections.singletonMap("id", id);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public List<Project> getAllProjects() {
        Project project = new Project();
        project.setId(2);
        project.setName("Test project");
        return asList(project);
    }


}
