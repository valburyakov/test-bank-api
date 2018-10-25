package com.test.bank.service;

import com.test.bank.model.Suite;
import com.test.bank.repository.SuitesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SuitesService {

    private final SuitesRepository suitesRepository;

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

    public boolean deleteSuite(Long id) {
        Optional<Suite> value = findSuiteById(id);
        if (!value.isPresent()) {
            return false;
        }

        Suite suite = value.get();
        suite.setDeleted(true);
        suitesRepository.save(suite);
        return true;
    }

}
