package com.test.bank.service;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Diff;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.repository.TestCaseRepository;
import com.test.bank.utils.DiffExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProjectsService projectsService;
    private final TestCaseMapper testCaseMapper;


    public TestCase addTestCase(Long id, TestCaseDTO testCaseDTO) {
        Project project = projectsService.findProjectById(id).get();
        TestCase testCase = testCaseMapper.toTestCase(testCaseDTO);
        Diff diff = DiffExtractor.of(new TestCase(), testCase);

        testCase.setDiff(diff);

        project.addTestCase(testCase);

        return testCase;
    }

    public List<TestCase> getAllTestCases(Long id) {
        return testCaseRepository.findAllByProjectId(id);
    }

    public TestCase updateTestCase(Long id, TestCaseDTO newTestCaseDTO) {
        TestCase oldTestCase = testCaseRepository.findById(id).get();

        TestCase newTestCase = testCaseMapper.toTestCase(newTestCaseDTO);

        Diff diff = DiffExtractor.of(oldTestCase, newTestCase);

        newTestCase.setId(oldTestCase.getId());

        newTestCase.setDiff(diff);

        return testCaseRepository.save(newTestCase);
    }

    public Optional<TestCase> getTestCaseById(Long id) {
        return testCaseRepository.findById(id);
    }
}
