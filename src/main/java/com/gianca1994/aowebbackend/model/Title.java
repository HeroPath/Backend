package com.gianca1994.aowebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "titles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int minLvl;

    @Column(nullable = false)
    private int minPts;

    public Title(String name, int requiredLevel, int requiredPoints) {
        this.name = name;
        this.minLvl = requiredLevel;
        this.minPts = requiredPoints;
    }
}
