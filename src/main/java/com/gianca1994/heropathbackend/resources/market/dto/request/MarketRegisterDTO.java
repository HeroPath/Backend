package com.gianca1994.heropathbackend.resources.market.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author: Gianca1994
 * @Explanation: DTO for register a new item in the market
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketRegisterDTO {
    private Long itemId;
    private Long goldPrice;
    private int diamondPrice;
}
