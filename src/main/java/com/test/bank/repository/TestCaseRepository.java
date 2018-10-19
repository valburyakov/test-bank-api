package com.test.bank.repository;

import com.test.bank.model.TestCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    @Query(value = "select * from test_case where suite_id = :suiteId and deleted = :deleted", nativeQuery = true)
    List<TestCase> findAllActiveTestCasesBySuiteId(@Param("suiteId") Long suiteId, @Param("deleted") boolean deleted);

    @Query(value = "select * from test_case where id IN (:ids) and deleted = :deleted", nativeQuery = true)
    List<TestCase> findAllActiveTestCasesByIds(@Param("ids") List<Long> ids, @Param("deleted") boolean deleted);

    @Query(value = "select test_case_id from test_case_labels where labels IN (:labels)", nativeQuery = true)
    List<Long> findTestCaseIdsByLabel(@Param("labels") List<String> labels);
}
