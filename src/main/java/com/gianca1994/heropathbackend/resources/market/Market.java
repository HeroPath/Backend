package com.gianca1994.heropathbackend.resources.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gianca1994.heropathbackend.resources.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class is the Market entity
 */

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
    private String usernameSeller;

    @Column
    private Long goldPrice;

    @Column
    private int diamondPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Item item;

    @Column
    @JsonIgnore
    private Long userId;

    public Market(Long userId, Item item, String usernameSeller, Long goldPrice, int diamondPrice) {
        this.userId = userId;
        this.item = item;
        this.usernameSeller = usernameSeller;
        this.goldPrice = goldPrice;
        this.diamondPrice = diamondPrice;
    }
}
