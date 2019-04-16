package com.test.bank.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(length = 10000)
    private String original;
    @Column(length = 10000)
    private String revised;
    @Column(length = 10000)
    private String unifiedDiff;
}
