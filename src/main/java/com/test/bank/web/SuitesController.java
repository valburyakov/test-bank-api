package com.test.bank.web;

import com.test.bank.model.Suite;
import com.test.bank.service.SuitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SuitesController {

    @Autowired
    private SuitesService suitesService;

    @RequestMapping(value = "/projects/{projectId}/suites", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createSuite(@PathVariable("projectId") Long projectId, @RequestBody Suite suite) {
        return new ResponseEntity<>(Collections.singletonMap("id", suitesService.add(projectId, suite)),HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/suites/{suiteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSuiteById(@PathVariable Long suiteId) {
        Optional<Suite> value = suitesService.findSuiteById(suiteId);
        if (!value.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + suiteId), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(value.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getSuitesByProjectId(@PathVariable Long projectId, Boolean deleted) {
        return new ResponseEntity<>(suitesService.findActiveSuitesByProjectId(projectId, deleted), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/suites/{suiteId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteSuite(@PathVariable Long suiteId) {
        Optional<Suite> value = suitesService.findSuiteById(suiteId);
        if (!value.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + suiteId), HttpStatus.NOT_FOUND);
        }
        Suite suite = value.get();
        suite.setDeleted(true);

        suitesService.updateSuite(suite);

        return new ResponseEntity<>(Collections.singletonMap("status", "Deleted"), HttpStatus.OK);
    }
}
