package com.gianca1994.heropathbackend.resources.market.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketRegisterDTO {
    private long itemId;
    private Long goldPrice;
    private int diamondPrice;
}
