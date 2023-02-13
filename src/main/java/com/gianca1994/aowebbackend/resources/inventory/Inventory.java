package com.gianca1994.aowebbackend.resources.inventory;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.resources.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;


/**
 * @Author: Gianca1994
 * Explanation: This class is the Inventory entity, it is used to store the items that the user has in his inventory.
 */

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "inventory_items",
            joinColumns = {@JoinColumn(name = "inventory_id")},
            inverseJoinColumns = {@JoinColumn(name = "items_id")}
    )
    private List<Item> items = new ArrayList<>();

}
