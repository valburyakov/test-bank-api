package com.test.bank.repository;

import com.test.bank.model.TestCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    @Query(value = "select * from test_case where suite_id = :suiteId and deleted = :deleted", nativeQuery = true)
    Iterable<TestCase> findAllActiveTestCasesBySuiteId(@Param("suiteId") Long suiteId, @Param("deleted") boolean deleted);
}
