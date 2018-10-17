package com.test.bank.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
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
    Long id;
    @ApiModelProperty(hidden = true)
    Long suiteId;
    @ApiModelProperty(example = "Demo test case")
    String name;
    @ApiModelProperty(example = "Test case description")
    String description;
    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "testCaseId")
    List<TestCaseStep> steps = new ArrayList<>();
    @ElementCollection(fetch = FetchType.LAZY)
    @ApiModelProperty(dataType="List", example = "[smoke, ui, api]")
    List<String> labels = new ArrayList<>();
    @ApiModelProperty(example = "NOT_TESTED")
    String status;
    @ApiModelProperty(hidden = true)
    boolean deleted;
}
