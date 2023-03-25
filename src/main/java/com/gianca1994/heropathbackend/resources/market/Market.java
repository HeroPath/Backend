package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "market")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long goldPrice;

    @Column
    private int diamondPrice;

    @Column
    private String usernameSeller;

}
