package com.test.bank.service;

import com.test.bank.model.Launch;
import com.test.bank.repository.LaunchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaunchesService {

    @Autowired
    private LaunchesRepository launchesRepository;

    public Long add(Launch launch) {
        return launchesRepository.save(launch).getId();
    }

    public void deleteById(Long id) {
        launchesRepository.deleteById(id);
    }

    public List<Launch> findAllLaunchesByProjectId(Long projectId) {
        return launchesRepository.findAllLaunchesByProjectId(projectId);
    }

    public List<Launch> findAllLaunchesByTestCaseId(Long testCaseId) {
        return launchesRepository.findAllLaunchesByTestCaseId(testCaseId);
    }

    public Optional<Launch> findLaunchById(Long id) {
        return launchesRepository.findById(id);
    }
}
