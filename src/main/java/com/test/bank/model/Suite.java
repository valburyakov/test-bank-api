package com.test.bank.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ApiModelProperty(example = "Demo test suite")
    @Column(unique = true)
    String name;
    @ApiModelProperty(hidden = true)
    boolean deleted;
}
