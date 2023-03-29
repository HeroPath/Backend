package com.gianca1994.heropathbackend.resources.market.dto.response;

import com.gianca1994.heropathbackend.resources.market.Market;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class is the Market All DTO
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketAllDTO {
    private Long userGold;
    private int userDiamond;
    private List<Market> markets;
}
