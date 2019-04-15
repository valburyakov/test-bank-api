package com.test.bank.mapper;

import com.test.bank.model.Project;
import com.test.bank.dto.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper MAPPER = Mappers.getMapper(ProjectMapper.class);

    ProjectDTO toProjectDto(Project project);
    Project toProject(ProjectDTO projectDTO);
}
