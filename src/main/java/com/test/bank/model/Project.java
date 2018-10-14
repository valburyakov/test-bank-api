package com.test.bank.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
}
