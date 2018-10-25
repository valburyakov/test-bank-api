package com.test.bank.service;

import com.test.bank.model.TestCase;
import com.test.bank.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class TestCasesService {

    private final TestCaseRepository testCaseRepository;

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


    public boolean deleteTestCase(Long id) {
        Optional<TestCase> value = findTestCaseById(id);
        if (!value.isPresent()) {
            return false;
        }

        TestCase testCase = value.get();
        testCase.setDeleted(true);
        testCaseRepository.save(testCase);
        return true;
    }
}
