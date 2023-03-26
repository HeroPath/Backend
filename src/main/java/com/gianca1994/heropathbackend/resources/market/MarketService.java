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

import javax.transaction.Transactional;
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


        if (!itemR.existsById(marketRegisterDTO.getItemId())) throw new BadRequest("Item not found");

        Item item = itemR.findById(marketRegisterDTO.getItemId()).get();
        if (item.isInMarket()) throw new BadRequest("Item already in market");
        if (!Objects.equals(item.getUser().getId(), userId)) throw new BadRequest("You don't own this item");
        item.setInMarket(true);
        item.setUser(null);

        Inventory userInventory = userR.findInventoryById(userId);
        userInventory.getItems().remove(item);

        userR.updateInventoryById(userId, userInventory);
        itemR.save(item);

        marketR.save(new Market(
                userId,
                item,
                usernameSeller,
                marketRegisterDTO.getGoldPrice(),
                marketRegisterDTO.getDiamondPrice()
        ));
    }

    @Transactional
    public void buyItem(Long userId, Long marketId) {
        if (!marketR.existsById(marketId)) throw new BadRequest("Item not found");
        Market market = marketR.findById(marketId).get();

        if (!userR.existsById(userId)) throw new BadRequest("User not found");
        if (!userR.existsById(market.getUserId())) throw new BadRequest("Seller not found");

        // GOLD ITEM
        Long itemGoldPrice = market.getGoldPrice();
        Long userBuyerGold = userR.findGoldByUserId(userId);
        if (userBuyerGold < itemGoldPrice) throw new BadRequest("You don't have enough gold");

        userBuyerGold -= itemGoldPrice;
        userR.updateGoldByUserId(userId, userBuyerGold);

        // DIAMOND ITEM
        int itemDiamondPrice = market.getDiamondPrice();
        int userBuyerDiamond = userR.findDiamondByUserId(userId);
        if (userBuyerDiamond < itemDiamondPrice) throw new BadRequest("You don't have enough diamond");

        userBuyerDiamond -= itemDiamondPrice;
        userR.updateUserDiamond(userId, userBuyerDiamond);

        // The gold and diamonds of the sold item are added to the seller user
        Long userSellerId = market.getUserId();
        Long userSellerGold = userR.findGoldByUserId(userSellerId) + itemGoldPrice;
        userR.updateGoldByUserId(userSellerId, userSellerGold);
        int userSellerDiamond = userR.findDiamondByUserId(userSellerId) + itemDiamondPrice;
        userR.updateUserDiamond(userSellerId, userSellerDiamond);

        System.out.println("USER BUYER: " + userId);
        System.out.println("USER SELLER: " + userSellerId);

    }

}
