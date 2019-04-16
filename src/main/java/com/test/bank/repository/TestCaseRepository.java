package com.test.bank.repository;

import com.test.bank.model.TestCase;
import org.springframework.data.repository.CrudRepository;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {
}
