package com.test.bank.web;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.model.TestCase;
import com.test.bank.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Collections.singletonMap;

@RestController
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/project/{id}/case")
    public ResponseEntity addTestCase(@PathVariable Long id, @RequestBody TestCaseDTO testCaseDTO) {
        return new ResponseEntity<>(singletonMap("id", testCaseService.addTestCase(id, testCaseDTO).getId()), HttpStatus.OK);
    }

    @GetMapping("/project/{id}/cases")
    public List<TestCase> getAllTestCases(@PathVariable Long id){
        return testCaseService.getAllTestCases(id);
    }

    @PutMapping("/case/{id}")
    public TestCase updateTestCase(@PathVariable Long id, @RequestBody TestCaseDTO testCaseDTO){
        return testCaseService.updateTestCase(id, testCaseDTO);
    }
}
