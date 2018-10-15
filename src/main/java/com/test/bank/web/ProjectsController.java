package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public Map<String, Long> createProject(@RequestBody Project project) {
        return Collections.singletonMap("id", projectsService.add(project));
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Map<String, Iterable<Project>> getAllProjects() {
        return Collections.singletonMap("projects", projectsService.getAllProjects());
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public Optional<Project> getProjectById(@PathVariable Long id) {
        return projectsService.findProjectById(id);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(@PathVariable Long id) {
        Optional<Project> project = projectsService.findProjectById(id);
        if (!project.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + id), HttpStatus.NOT_FOUND);
        }

        projectsService.deleteProjectById(id);
        return new ResponseEntity<>(Collections.singletonMap("status", "Deleted"), HttpStatus.OK);
    }
}
