package com.test.bank.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SuitesRepository extends CrudRepository<Suite, Long> {
    List<Suite> findSuitesByProjectId(Long projectId);

    @Query(value = "SELECT * " +
            "FROM suite " +
            "WHERE project_id = :projectId " +
            "AND deleted = :deleted", nativeQuery = true)
    List<Suite> findActiveSuitesByProjectId(@Param("projectId") Long projectId, @Param("deleted") boolean deleted);

    @Query(value = "select * from suite where project_id = :projectId and name = :name and deleted = :deleted", nativeQuery = true)
    Optional<Suite> findSuitByName(@Param("projectId") Long projectId, @Param("name") String name, @Param("deleted") boolean deleted);
}
