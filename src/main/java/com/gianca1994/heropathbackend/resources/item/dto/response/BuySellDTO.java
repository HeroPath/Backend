package com.gianca1994.heropathbackend.resources.item.dto.response;

import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to return the user's gold and inventory after a buy or sell action
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuySellDTO {
    private long userGold;
    private Inventory inventory;
}
