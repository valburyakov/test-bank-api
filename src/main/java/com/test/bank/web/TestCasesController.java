package com.test.bank.web;

import com.google.common.base.Enums;
import com.test.bank.enums.TestCaseStatus;
import com.test.bank.model.TestCase;
import com.test.bank.service.ProjectsService;
import com.test.bank.service.SuitesService;
import com.test.bank.service.TestCasesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class TestCasesController {

    private static final String STATUS_KEY = "status";
    private static final String ID_KEY = "id";
    private static final String DELETED_STATUS = "Deleted";

    private final TestCasesService testCasesService;
    private final ProjectsService projectsService;
    private final SuitesService suitesService;


    @RequestMapping(value = "/suites/{suiteId}/cases", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesBySuiteId(@PathVariable Long suiteId, Boolean deleted) {
        return suitesService.findSuiteById(suiteId)
                .<ResponseEntity>map(suite -> new ResponseEntity<>(testCasesService.findActiveTestCasesBySuiteId(suiteId, deleted), OK))
                .orElseGet(() -> suiteIdNotFoundResponse(suiteId));
    }

    @RequestMapping(value = "/projects/{projectId}/cases/labels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesByLabels(@PathVariable Long projectId, @RequestParam("label") List<String> labels) {
        return projectsService.findProjectById(projectId)
                .<ResponseEntity>map(project -> new ResponseEntity<>(testCasesService.findByLabel(labels), OK))
                .orElseGet(() -> projectIdNotFoundResponse(projectId));
    }

    @RequestMapping(value = "/suites/cases/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTestCasesById(@PathVariable Long id) {
        return testCasesService.findTestCaseById(id)
                .<ResponseEntity>map(testCase -> new ResponseEntity<>(testCase, OK))
                .orElseGet(() -> testCaseNotFoundResponse(id));
    }

    @RequestMapping(value = "/suites/{suiteId}/cases", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTestCase(@PathVariable Long suiteId, @RequestBody TestCase testCase) {
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

    @RequestMapping(value = "/suites/cases/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteTestCaseById(@PathVariable Long id) {
        boolean deleted = testCasesService.deleteTestCase(id);
        if (!deleted) {
            return testCaseNotFoundResponse(id);
        }

        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, DELETED_STATUS), HttpStatus.OK);
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

    private ResponseEntity testCaseNotFoundResponse(Long testCaseId) {
        return new ResponseEntity<>(Collections.singletonMap(STATUS_KEY, "No such test case with id " + testCaseId), HttpStatus.NOT_FOUND);
    }
}

