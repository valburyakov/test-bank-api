package com.test.bank.model;

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
public class Diff {

    public Diff(String original, String revised, String unifiedDiff) {
        this.original = original;
        this.revised = revised;
        this.unifiedDiff = unifiedDiff;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String original;
    private String revised;
    private String unifiedDiff;
}