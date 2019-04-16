package com.test.bank.web;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.singletonMap;

@RestController
@RequiredArgsConstructor
public class TestCaseController {

    private final TestCaseService testCaseService;

    @PostMapping("/project/{id}/case")
    public ResponseEntity addTestCase(@PathVariable Long id, @RequestBody TestCaseDTO testCaseDTO) {
        return new ResponseEntity<>(singletonMap("id", testCaseService.addTestCase(id, testCaseDTO).getId()), HttpStatus.OK);
    }
}
