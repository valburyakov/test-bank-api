package com.test.bank.web;

import com.test.bank.model.Project;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public Project greeting(@RequestBody Project project) {
        project.setId(1);
        return project;
    }

}
