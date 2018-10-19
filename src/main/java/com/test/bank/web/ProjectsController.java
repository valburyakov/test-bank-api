package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public Map<String, Long> createProject(@RequestBody Project project) {
        return singletonMap("id", projectsService.addProject(project));
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(@PathVariable Long id) {
        return projectsService.findProjectById(id)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap("status", "No such project with id " + id), NOT_FOUND));
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(@PathVariable Long id) {
        boolean deleted = projectsService.deleteProject(id);
        if (!deleted) {
            return new ResponseEntity<>(singletonMap("status", "No such project with id " + id), NOT_FOUND);
        }

        return new ResponseEntity<>(singletonMap("status", "Deleted"), OK);
    }
}
