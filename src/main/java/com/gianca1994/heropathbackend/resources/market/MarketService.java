package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketR;

    @Autowired
    private ItemRepository itemR;

    public List<Market> getAllMarkets() {
        return marketR.findAll();
    }

    public void registerItem(Long userId, String usernameSeller, MarketRegisterDTO marketRegisterDTO) {
        Market market = new Market();
        market.setUserId(userId);
        market.setItem(itemR.findById(marketRegisterDTO.getItemId()).get());
        market.setUsernameSeller(usernameSeller);
        market.setGoldPrice(marketRegisterDTO.getGoldPrice());
        market.setDiamondPrice(marketRegisterDTO.getDiamondPrice());
        marketR.save(market);

    }
}
