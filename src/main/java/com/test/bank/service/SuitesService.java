package com.test.bank.service;

import com.test.bank.model.Suite;
import com.test.bank.repository.SuitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuitesService {

    @Autowired
    private SuitesRepository suitesRepository;

    public Suite add(Long projectId, Suite suite) {
        suite.setProjectId(projectId);
        return suitesRepository.save(suite);
    }

    public Optional<Suite> findSuiteById(Long id) {
        return suitesRepository.findById(id);
    }

    public List<Suite> findSuitesByProjectId(Long projectId) {
        return suitesRepository.findSuitesByProjectId(projectId);
    }

    public List<Suite> findActiveSuitesByProjectId(Long projectId) {
        return suitesRepository.findActiveSuitesByProjectId(projectId, false);
    }

    public void deleteSuite(Suite suite) {
        suitesRepository.delete(suite);
    }

    public void updateSuite(Suite suite) {
        suitesRepository.save(suite);
    }
}
