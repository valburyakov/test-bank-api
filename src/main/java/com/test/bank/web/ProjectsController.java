package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.dto.ProjectDTO;
import com.test.bank.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static com.test.bank.ControllerKeyConstants.ID_KEY;
import static com.test.bank.ControllerKeyConstants.STATUS_KEY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectsService projectsService;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public ResponseEntity createProject(@Valid @RequestBody ProjectDTO project) {
        return new ResponseEntity<>(singletonMap(ID_KEY, projectsService.addProject(project)), OK);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects() {
        return projectsService.getAllProjects();
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public ResponseEntity getProjectById(@PathVariable Long id) {
        return projectsService.findProjectById(id)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id " + id), NOT_FOUND));
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity getProjectByName(@RequestParam  String name){
        return projectsService.getProjectByName(name)
                .<ResponseEntity>map(project -> new ResponseEntity<>(project, OK))
                .orElseGet(() -> new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id or name " + name), NOT_FOUND));
    }
}


