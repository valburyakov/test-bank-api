package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public Map<String, Long> createProject(@RequestBody Project project) {
        return Collections.singletonMap("id", projectsService.add(project).getId());
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Map<String, Iterable<Project>> getAllProjects() {
        return Collections.singletonMap("projects", projectsService.getAllProjects());
    }
}
