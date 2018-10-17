package com.test.bank.web;

import com.google.common.base.Enums;
import com.test.bank.TestCaseStatus;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TestCasesController {

    private static final String STATUS_KEY = "status";
    private static final String ID_KEY = "id";
    private static final String CASE_KEY = "case";
    private static final String CASES_KEY = "cases";
    private static final String DELETED_STATUS = "Deleted";

    @Autowired
    private TestCasesService testCasesService;

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private SuitesService suitesService;


    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesBySuiteId(@PathVariable Long projectId, @PathVariable Long suiteId, Boolean deleted) {
        if (!isProjectPresent(projectId)) {
            return projectIdNotFoundResponse(projectId);
        }
        if (!isSuitePresent(suiteId)) {
            return suiteIdNotFoundResponse(suiteId);
        }

        return new ResponseEntity<>(Collections.singletonMap(CASES_KEY, testCasesService.findActiveTestCasesBySuiteId(suiteId, deleted)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/cases/labels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesByLabels(@PathVariable Long projectId, @RequestParam("label") List<String> labels) {
        if (!isProjectPresent(projectId)) {
            return projectIdNotFoundResponse(projectId);
        }

        return new ResponseEntity<>(Collections.singletonMap(CASES_KEY, testCasesService.findByLabel(labels)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/cases/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesByProjectIdAndCaseId(@PathVariable Long projectId, @PathVariable Long id) {
        if (!isProjectPresent(projectId)) {
            return projectIdNotFoundResponse(projectId);
        }

        Optional<TestCase> testCase = testCasesService.findTestCaseById(id);
        if (!testCase.isPresent()) {
            return testCaseNotFoundResponse(id);
        }

        return new ResponseEntity<>(Collections.singletonMap(CASE_KEY, testCasesService.findTestCaseById(id)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/suites/{suiteId}/cases", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTestCase(@PathVariable Long projectId, @PathVariable Long suiteId, @RequestBody TestCase testCase) {
        if (!isProjectPresent(projectId)) {
            return projectIdNotFoundResponse(projectId);
        }
        if (!isSuitePresent(suiteId)) {
            return suiteIdNotFoundResponse(suiteId);
        }
        if (!isStatusCorrect(testCase.getStatus())) {
            return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY,
                    String.format("No such status %s found for test cases, use on of %s status.",
                            testCase.getStatus(), Stream.of(TestCaseStatus.values())
                                    .map(Enum::name)
                                    .collect(Collectors.joining(", ")))), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(Collections.singletonMap(ID_KEY, testCasesService.add(suiteId, testCase)), HttpStatus.OK);
    }

    @RequestMapping(value = "/projects/{projectId}/cases/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteTestCaseById(@PathVariable Long projectId, @PathVariable Long id) {
        if (!isProjectPresent(projectId)) {
            return projectIdNotFoundResponse(projectId);
        }

        Optional<TestCase> value = testCasesService.findTestCaseById(id);
        if (!value.isPresent()) {
            return testCaseNotFoundResponse(id);
        }

        TestCase test = value.get();
        test.setDeleted(true);

        testCasesService.updateTestCase(test);
        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, DELETED_STATUS), HttpStatus.OK);
    }


    private boolean isProjectPresent(Long projectId) {
        return projectsService.findProjectById(projectId).isPresent();
    }

    private boolean isSuitePresent(Long suiteId) {
        return suitesService.findSuiteById(suiteId).isPresent();
    }

    private boolean isStatusCorrect(String status) {
        return Enums.getIfPresent(TestCaseStatus.class, status).isPresent();
    }


    private ResponseEntity projectIdNotFoundResponse(Long projectId) {
        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such project with id " + projectId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity suiteIdNotFoundResponse(Long suiteId) {
        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such suite with id " + suiteId), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity testCaseNotFoundResponse(Long suiteId) {
        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such test case with id " + suiteId), HttpStatus.NOT_FOUND);
    }

}

