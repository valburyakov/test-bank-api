package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity createProject(@RequestBody Project project) {
        if (project.getName() == null || project.getName().isEmpty()) {
            return new ResponseEntity<>(singletonMap(STATUS_KEY, "Wrong project name"), BAD_REQUEST);
        }

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

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProjectById(@PathVariable Long id) {
        boolean deleted = projectsService.deleteProject(id);
        if (!deleted) {
            return new ResponseEntity<>(singletonMap(STATUS_KEY, "No such project with id " + id), NOT_FOUND);
        }

        return new ResponseEntity<>(singletonMap(STATUS_KEY, "Deleted"), OK);
    }
}


