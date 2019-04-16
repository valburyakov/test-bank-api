package com.test.bank.repository;

import com.test.bank.model.TestCase;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {
    List<TestCase> findAllByProjectId(Long id);
}
