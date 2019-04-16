package com.test.bank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    private boolean deleted;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "projectToCase",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "projectId")}
    )
    @OrderBy("id")
    private List<TestCase> testCaseList = new ArrayList<>();

    public void addTestCase(TestCase testCase) {
        testCaseList.add(testCase);
        testCase.setProject(this);
    }

    public void removeTestCase(Long caseId) {
        TestCase testCase = testCaseList.stream().filter(it -> it.getId().equals(caseId))
                .findFirst().get();

        testCaseList.remove(testCase);
        testCase.setProject(null);
    }
}
