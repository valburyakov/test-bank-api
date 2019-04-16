package com.test.bank.service;

import com.test.bank.dto.TestCaseDTO;
import com.test.bank.mapper.TestCaseMapper;
import com.test.bank.model.Project;
import com.test.bank.model.TestCase;
import com.test.bank.repository.ProjectsRepository;
import com.test.bank.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ProjectsService projectsService;
    private final TestCaseMapper testCaseMapper;
    private final ProjectsRepository projectsRepository;


    public Project addTestCase(Long id, TestCaseDTO testCaseDTO) {
        Project project = projectsService.findProjectById(id).get();
        TestCase testCase = testCaseMapper.toTestCase(testCaseDTO);
        project.addTestCase(testCase);

        return projectsRepository.save(project);
    }
}
