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
        String diff = DiffExtractor.generateDiff("", testCase.getTitle());

        testCase.setDiff(new Diff(diff));

        project.addTestCase(testCase);

        return testCase;
    }

    public List<TestCase> getAllTestCases(Long id) {
        return testCaseRepository.findAllByProjectId(id);
    }

    public TestCase updateTestCase(Long id, TestCaseDTO newTestCaseDTO) {
        TestCase oldTestCase = testCaseRepository.findById(id).get();

        String diff = DiffExtractor.generateDiff(oldTestCase.getTitle(), newTestCaseDTO.getTitle());

        oldTestCase.setTitle(newTestCaseDTO.getTitle());
        oldTestCase.setReference(newTestCaseDTO.getReference());

        oldTestCase.setDiff(new Diff(diff));

        return testCaseRepository.save(oldTestCase);
    }
}
