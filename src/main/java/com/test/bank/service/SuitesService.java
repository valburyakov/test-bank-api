package com.test.bank.service;

import com.test.bank.model.Suite;
import com.test.bank.repository.SuitesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuitesService {

    @Autowired
    private SuitesRepository suitesRepository;

    public Suite add(Long projectId, Suite suite) {
        suite.setProjectId(projectId);
        return suitesRepository.save(suite);
    }
}
