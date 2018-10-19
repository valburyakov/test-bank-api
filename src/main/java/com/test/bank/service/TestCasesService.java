package com.test.bank.service;

import com.test.bank.model.TestCase;
import com.test.bank.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestCasesService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public Long add(Long suiteId, TestCase testCase) {
        testCase.setSuiteId(suiteId);
        return testCaseRepository.save(testCase).getId();
    }

    public List<TestCase> findActiveTestCasesBySuiteId(Long suiteId, boolean deleted) {
        return testCaseRepository.findAllActiveTestCasesBySuiteId(suiteId, deleted);
    }

    public List<TestCase> findByLabel(List<String> labels) {
        List<Long> ids = testCaseRepository.findTestCaseIdsByLabel(labels);
        return findAllActiveTestCasesByIds(ids, false);
    }

    public List<TestCase> findAllActiveTestCasesByIds(List<Long> ids,  boolean deleted) {
        return testCaseRepository.findAllActiveTestCasesByIds(ids, deleted);
    }

    public Optional<TestCase> findTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }


    public void updateTestCase(TestCase testCase) {
        testCaseRepository.save(testCase);
    }
}
