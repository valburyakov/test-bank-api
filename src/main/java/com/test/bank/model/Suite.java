package com.test.bank.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Suite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    Long id;
    @ApiModelProperty(hidden = true)
    Long projectId;
    @NotNull
    @Size(max = 20)
    @Column(unique=true, nullable=false)
    @ApiModelProperty(example = "Test suite")
    String name;
    @ApiModelProperty(example = "false")
    boolean deleted;
}
