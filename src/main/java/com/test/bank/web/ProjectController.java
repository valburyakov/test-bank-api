package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.dto.ProjectDTO;
import com.test.bank.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static com.test.bank.ControllerKeyConstants.ID_KEY;
import static com.test.bank.ControllerKeyConstants.STATUS_KEY;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectsService projectsService;

    @PostMapping("/project")
    public ResponseEntity createProject(@Valid @RequestBody ProjectDTO project) {
        return new ResponseEntity<>(singletonMap(ID_KEY, projectsService.addProject(project)), OK);
    }

    @GetMapping(value = "/projects")
    public Iterable<Project> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @GetMapping(value = "/project/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id) {
        return projectsService.findProjectById(id)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id " + id), NOT_FOUND));
    }

    @GetMapping(value = "/project")
    public ResponseEntity getProjectByName(@RequestParam String name) {
        return projectsService.getProjectByName(name)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id or name " + name), NOT_FOUND));
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectsService.deleteProject(id);
        return new ResponseEntity<>(singletonMap(STATUS_KEY, "Deleted"), OK);
    }
}


