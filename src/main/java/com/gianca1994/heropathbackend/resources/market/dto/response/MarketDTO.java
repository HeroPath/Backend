package com.gianca1994.heropathbackend.resources.market.dto.response;

import com.gianca1994.heropathbackend.resources.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO {
    private Long id;
    private String usernameSeller;
    private Long goldPrice;
    private int diamondPrice;
    private Item item;
}
