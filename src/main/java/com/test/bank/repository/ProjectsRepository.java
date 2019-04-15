package com.test.bank.repository;

import com.test.bank.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectsRepository extends CrudRepository<Project, Long> {
    Optional<Project> findByName(String idOrName);
}
