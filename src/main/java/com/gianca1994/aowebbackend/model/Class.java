package com.gianca1994.aowebbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "classes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String name;
    @Column
    private int strength;
    @Column
    private int dexterity;
    @Column
    private int intelligence;
    @Column
    private int vitality;
    @Column
    private int luck;
}
