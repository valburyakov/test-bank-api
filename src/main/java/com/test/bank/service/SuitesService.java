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

    public Long add(Long projectId, Suite suite) {
        suite.setProjectId(projectId);
        return suitesRepository.save(suite).getId();
    }

    public Optional<Suite> findSuiteById(Long id) {
        return suitesRepository.findById(id);
    }
    public Optional<Suite> findSuitByName(Long projectId, String name, boolean deleted) {
        return suitesRepository.findSuitByName(projectId, name, deleted);
    }

    public List<Suite> findSuitesByProjectId(Long projectId) {
        return suitesRepository.findSuitesByProjectId(projectId);
    }

    public List<Suite> findActiveSuitesByProjectId(Long projectId, boolean deleted) {
        return suitesRepository.findActiveSuitesByProjectId(projectId, deleted);
    }

    public void deleteSuite(Suite suite) {
        suitesRepository.delete(suite);
    }

    public void updateSuite(Suite suite) {
        suitesRepository.save(suite);
    }
}
