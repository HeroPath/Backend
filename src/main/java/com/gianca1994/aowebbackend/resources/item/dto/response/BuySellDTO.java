package com.gianca1994.aowebbackend.resources.item.dto.response;

import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuySellDTO {
    private long userGold;
    private Inventory inventory;
}
