package com.test.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.difflib.text.DiffRow;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;
    private String reference;
    private Date createdAt = new Date();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "episodeId")
    private Project project;

    @OneToOne(cascade = {CascadeType.ALL})
    private Diff diff;
}
