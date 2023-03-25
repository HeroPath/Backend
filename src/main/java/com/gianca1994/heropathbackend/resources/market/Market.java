package com.gianca1994.heropathbackend.resources.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.market.marketRelations.MarketItem;
import com.gianca1994.heropathbackend.resources.user.userRelations.userMail.UserMail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "markets")
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

    @OneToMany(mappedBy = "market", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<MarketItem> marketItems;

}
