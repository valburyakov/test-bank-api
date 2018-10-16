package com.test.bank.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    long id;
    @ApiModelProperty(hidden = true)
    long suiteId;
    @ApiModelProperty(example = "Demo test case")
    String name;
    @ApiModelProperty(example = "Test case description")
    String description;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "testCaseId")
    List<TestCaseStep> steps;
    @ApiModelProperty(example = "NOT_TESTED")
    String status;
    @ApiModelProperty(hidden = true)
    boolean deleted;
}
