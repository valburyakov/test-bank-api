package com.test.bank.service;

import com.google.common.base.Enums;
import com.test.bank.enums.LaunchStatus;
import com.test.bank.model.Launch;
import com.test.bank.repository.LaunchesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LaunchesService {

    private final LaunchesRepository launchesRepository;

    public Long add(Launch launch) {
        return launchesRepository.save(launch).getId();
    }

    public Long add(Long projectId, Launch launch) {
        launch.setProjectId(projectId);
        return launchesRepository.save(launch).getId();
    }

    public boolean deleteById(Long id) {
        Optional<Launch> value = findLaunchById(id);
        if (!value.isPresent()) {
            return false;
        }

        Launch launch = value.get();
        launch.setDeleted(true);

        launchesRepository.save(launch);
        return true;
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

    private boolean isResultCorrect(String status) {
        return Enums.getIfPresent(LaunchStatus.class, status).isPresent();
    }
}
