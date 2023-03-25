package com.gianca1994.heropathbackend.resources.market.marketRelations;



import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.market.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "market_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
