package com.test.bank.repository;

import com.test.bank.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectsRepository extends CrudRepository<Project, Long> {
}
