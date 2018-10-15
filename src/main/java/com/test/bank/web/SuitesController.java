package com.test.bank.web;

import com.test.bank.model.Project;
import com.test.bank.model.Suite;
import com.test.bank.service.SuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class SuitesController {

    @Autowired
    private SuitesService suitesService;

    @RequestMapping(value = "/projects/{projectId}/suites", method = RequestMethod.POST)
    public Map<String, Long> createSuite(@RequestParam Long projectId, @RequestBody Suite suite) {
        return Collections.singletonMap("id", suitesService.add(projectId, suite).getId());
    }
}
