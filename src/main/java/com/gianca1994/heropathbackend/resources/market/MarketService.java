package com.gianca1994.heropathbackend.resources.market;

import com.gianca1994.heropathbackend.exception.BadRequest;
import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.ItemRepository;
import com.gianca1994.heropathbackend.resources.market.dto.request.MarketRegisterDTO;
import com.gianca1994.heropathbackend.resources.user.User;
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

    @Autowired
    private UserRepository userR;

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
        item.setUser(null);

        Inventory userInventory = userR.findInventoryById(userId);
        userInventory.getItems().remove(item);

        market.setUserId(userId);
        market.setItem(item);
        market.setUsernameSeller(usernameSeller);
        market.setGoldPrice(marketRegisterDTO.getGoldPrice());
        market.setDiamondPrice(marketRegisterDTO.getDiamondPrice());

        userR.updateInventoryById(userId, userInventory);
        itemR.save(item);
        marketR.save(market);
    }

    public void buyItem(Long userId, Long marketId) {
        if (!marketR.existsById(marketId)) throw new BadRequest("Item not found");
        Market market = marketR.findById(marketId).get();
        if (Objects.equals(market.getUserId(), userId)) throw new BadRequest("You can't buy your own item");
        if (market.getItem().isInMarket()) throw new BadRequest("Item already sold");
        market.getItem().setInMarket(true);
        marketR.save(market);
    }
}
