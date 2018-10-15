package com.test.bank.web;

import com.test.bank.model.TestCase;
import com.test.bank.service.ProjectsService;
import com.test.bank.service.SuitesService;
import com.test.bank.service.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
public class TestCasesController {

    @Autowired
    private TestCasesService testCasesService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private SuitesService suitesService;


    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesBySuiteId(@PathVariable Long projectId, @PathVariable Long suiteId, Boolean deleted) {
        if (!isProjectPresent(projectId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + projectId), HttpStatus.NOT_FOUND);
        }
        if (!isSuitePresent(suiteId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such suite with id " + suiteId), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Collections.singletonMap("cases", testCasesService.findActiveTestCasesBySuiteId(suiteId, deleted)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesBySuiteIdAndCaseId(@PathVariable Long projectId, @PathVariable Long suiteId,  @PathVariable Long id) {
        if (!isProjectPresent(projectId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + projectId), HttpStatus.NOT_FOUND);
        }
        if (!isSuitePresent(suiteId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such suite with id " + suiteId), HttpStatus.NOT_FOUND);
        }

        Optional<TestCase> testCase = testCasesService.findTestCaseById(id);
        if (!testCase.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such test case with id " + id), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Collections.singletonMap("case", testCasesService.findTestCaseById(id)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTestCase(@PathVariable Long projectId, @PathVariable Long suiteId, @RequestBody TestCase testCase) {
        if (!isProjectPresent(projectId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " +
                    testCase.getSuiteId()), HttpStatus.NOT_FOUND);
        }
        if (!isSuitePresent(suiteId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such suite with id " +
                    testCase.getSuiteId()), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Collections.singletonMap("id", testCasesService.add(suiteId, testCase).getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteTestCaseById(@PathVariable Long projectId, @PathVariable Long suiteId, @PathVariable Long id) {
        if (!isProjectPresent(projectId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such project with id " + projectId), HttpStatus.NOT_FOUND);
        }
        if (!isSuitePresent(suiteId)) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such suite with id " + suiteId), HttpStatus.NOT_FOUND);
        }

        Optional<TestCase> testCase = testCasesService.findTestCaseById(id);
        if (!testCase.isPresent()) {
            return new ResponseEntity<>(Collections.singletonMap("status", "No such test case with id " + id), HttpStatus.NOT_FOUND);
        }

        TestCase test = testCase.get();
        test.setDeleted(true);

        testCasesService.updateTestCase(test);
        return new ResponseEntity<>(Collections.singletonMap("status", "Deleted"), HttpStatus.OK);
    }


    private boolean isProjectPresent(Long projectId) {
        return projectsService.findProjectById(projectId).isPresent();
    }

    private boolean isSuitePresent(Long suiteId) {
        return suitesService.findSuiteById(suiteId).isPresent();
    }

}
