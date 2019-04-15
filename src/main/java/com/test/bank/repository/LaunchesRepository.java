package com.test.bank.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LaunchesRepository extends CrudRepository<Launch, Long> {

    @Query(value = "select * from launch where project_id = :projectId", nativeQuery = true)
    List<Launch> findAllLaunchesByProjectId(@Param("projectId") Long projectId);

    @Query(value = "select * from launch where test_case_id = :testCaseId", nativeQuery = true)
    List<Launch> findAllLaunchesByTestCaseId(@Param("testCaseId") Long testCaseId);
}
