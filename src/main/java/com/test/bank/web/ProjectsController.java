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

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public Map<String, Long> createProject(@RequestBody Project project) {
        return singletonMap("id", projectsService.add(project));
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Map<String, Iterable<Project>> getAllProjects() {
        return singletonMap("projects", projectsService.getAllProjects());
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(@PathVariable Long id) {
        Optional<Project> value = projectsService.findProjectById(id);
        return value.<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap("status", "No such project with id " + id), NOT_FOUND));
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(@PathVariable Long id) {
        Optional<Project> value = projectsService.findProjectById(id);
        if (!value.isPresent()) {
            return new ResponseEntity<>(singletonMap("status", "No such project with id " + id), NOT_FOUND);
        }

        Project project = value.get();
        project.setDeleted(true);
        projectsService.add(project);
        return new ResponseEntity<>(singletonMap("status", "Deleted"), OK);
    }
}
