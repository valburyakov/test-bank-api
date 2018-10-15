package com.test.bank.repository;

import com.test.bank.model.Suite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SuitesRepository extends CrudRepository<Suite, Long> {
    List<Suite> findSuitesByProjectId(Long projectId);

    @Query(value = "SELECT * " +
            "FROM suite " +
            "WHERE project_id = :projectId " +
            "AND deleted = :deleted", nativeQuery = true)
    List<Suite> findActiveSuitesByProjectId(@Param("projectId") Long projectId, @Param("deleted") boolean deleted);
}
