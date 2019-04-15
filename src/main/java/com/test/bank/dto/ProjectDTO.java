package com.test.bank.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDTO {

    @ApiModelProperty(example = "Test project")
    @Column(unique = true)
    @NotBlank
    String name;
    @ApiModelProperty(example = "false")
    boolean deleted;
}
