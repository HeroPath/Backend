package com.gianca1994.heropathbackend.resources.equipment;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.heropathbackend.resources.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * @Author: Gianca1994
 * @Explanation: This class is the model of the equipment table in the database.
 */

@Entity
@Table(name = "equipments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "equipment_items",
            joinColumns = {@JoinColumn(name = "equipment_id")},
            inverseJoinColumns = {@JoinColumn(name = "items_id")}
    )
    private Set<Item> items = new HashSet<>();
}
