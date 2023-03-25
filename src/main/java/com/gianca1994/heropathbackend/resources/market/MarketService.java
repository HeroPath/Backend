package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {

    @Autowired
    private MarketRepository marketRepository;

    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    public void registerItem(MarketRegisterDTO marketRegisterDTO) {
        Market market = new Market();
        market.setUserId();
        market.setItemId(marketRegisterDTO.getItemId());
        market.setUsernameSeller();
        market.setGoldPrice(marketRegisterDTO.getGoldPrice());
        market.setDiamondPrice(marketRegisterDTO.getDiamondPrice());
        marketRepository.save(market);

    }
}
