package com.test.bank.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Launch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    Long id;
    @ApiModelProperty(hidden = true)
    @Column(nullable = false)
    Long projectId;
    Long testCaseId;
    @ApiModelProperty(example = "NOT_RUN")
    String result;
    String description = "";
    @ApiModelProperty(hidden = true)
    @CreationTimestamp
    Date executionDate;
    @ApiModelProperty(example = "false")
    boolean deleted;
}
