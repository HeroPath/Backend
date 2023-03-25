package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.exception.BadRequest;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

        if (!itemR.existsById(marketRegisterDTO.getItemId())) throw new BadRequest("Item not found");

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        if (item.isInMarket()) throw new BadRequest("Item already in market");
        if (!Objects.equals(item.getUser().getId(), userId)) throw new BadRequest("You don't own this item");
        item.setInMarket(true);

        market.setUserId(userId);
        market.setItem(item);
        market.setUsernameSeller(usernameSeller);
        market.setGoldPrice(marketRegisterDTO.getGoldPrice());
        market.setDiamondPrice(marketRegisterDTO.getDiamondPrice());

        itemR.save(item);
        marketR.save(market);
    }
}
