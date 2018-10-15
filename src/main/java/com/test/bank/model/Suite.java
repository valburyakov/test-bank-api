package com.test.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Suite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @JsonIgnore
    Long projectId;

    String name;
}
