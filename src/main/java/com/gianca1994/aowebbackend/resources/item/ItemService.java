package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.BuySellDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.EquipOrUnequipDTO;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemConst;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemServiceValidator;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of the business logic of the items.
 */

@Service
public class ItemService {

    ItemServiceValidator validator = new ItemServiceValidator();

    @Autowired
    private ItemRepository itemR;

    @Autowired
    private UserRepository userR;

    public List<Item> getClassShop(String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of returning a list of items that are available for a class.
         * @param String aClass
         * @return List<Item>
         */
        return itemR.findByClassRequiredAndUserIsNullAndShopIsTrueOrderByLvlMinAsc(aClass);
    }

    public void saveItem(ItemDTO newItem) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param ItemDTO newItem
         * @return none
         */
        validator.checkDtoToSaveItem(newItem);
        validator.itemExists(itemR.existsByName(newItem.getName().toLowerCase()));

        itemR.save(new Item(
                newItem.getName().toLowerCase(), newItem.getType(), newItem.getLvlMin(), newItem.getPrice(),
                newItem.getClassRequired().equals("") ? "none" : newItem.getClassRequired(),
                newItem.getStrength(), newItem.getDexterity(), newItem.getIntelligence(), newItem.getVitality(), newItem.getLuck(),
                newItem.isShop()
        ));
    }

    public BuySellDTO buyItem(Long userId, Long itemBuyId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param long userId
         * @param long itemBuyId
         * @return BuySellDTO
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemBuyId));
        validator.checkItemFromTrader(itemR.isUserIdNull(itemBuyId));
        User user = userR.findById(userId).get();
        Item itemBuy = itemR.findById(itemBuyId).get();

        validator.checkInventoryFull(user.getInventory().getItems().size());
        validator.checkGoldEnough(user.getGold(), itemBuy.getPrice());
        user.setGold(user.getGold() - itemBuy.getPrice());

        Item newItemBuy = new Item(
                itemBuy.getName(), itemBuy.getType(), itemBuy.getLvlMin(), itemBuy.getPrice() / 2, itemBuy.getClassRequired(),
                itemBuy.getQuality(), itemBuy.getItemLevel(), itemBuy.getStrength(), itemBuy.getDexterity(), itemBuy.getIntelligence(),
                itemBuy.getVitality(), itemBuy.getLuck(), itemBuy.isShop() , user
        );
        user.getInventory().getItems().add(newItemBuy);
        itemR.save(newItemBuy);
        userR.save(user);
        return new BuySellDTO(user.getGold(), user.getInventory());
    }

    public BuySellDTO sellItem(Long userId, Long itemSellId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param long userId
         * @param long itemSellId
         * @return BuySellDTO
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemSellId));
        validator.checkItemNotInPossession(itemR.isUserIdNull(itemSellId));

        User user = userR.findById(userId).get();
        Item itemSell = itemR.findById(itemSellId).get();
        validator.inventoryContainsItem(user.getInventory().getItems(), itemSell);

        user.setGold(user.getGold() + (itemSell.getPrice()));
        user.getInventory().getItems().remove(itemSell);
        itemR.delete(itemSell);
        userR.save(user);
        return new BuySellDTO(user.getGold(), user.getInventory());
    }

    public EquipOrUnequipDTO equipItem(Long userId, Long itemId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping an item.
         * @param long userId
         * @param long itemId
         * @return EquipOrUnequipDTO
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemId));
        User user = userR.findById(userId).get();
        Item itemEquip = itemR.findById(itemId).get();

        validator.checkItemEquipIfPermitted(itemEquip.getType());
        validator.checkEquipOnlyOneType(user.getEquipment().getItems(), itemEquip.getType());
        validator.inventoryContainsItem(user.getInventory().getItems(), itemEquip);
        validator.checkItemClassEquip(user.getAClass(), itemEquip.getClassRequired());
        validator.checkItemLevelEquip(user.getLevel(), itemEquip.getLvlMin());

        user.getInventory().getItems().remove(itemEquip);
        boolean isPotion = itemEquip.getType().equals(ItemConst.POTION_TYPE);

        if (!isPotion) {
            user.getEquipment().getItems().add(itemEquip);
            user.swapItemToEquipmentOrInventory(itemEquip, true);
        } else user.setHp(user.getMaxHp());
        userR.save(user);
        if (isPotion) itemR.delete(itemEquip);
        return new EquipOrUnequipDTO(user);
    }

    public EquipOrUnequipDTO unequipItem(Long userId, Long itemId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of unequipping an item.
         * @param long userId
         * @param long itemId
         * @return EquipOrUnequipDTO
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemId));
        User user = userR.findById(userId).get();
        Item itemUnequip = itemR.findById(itemId).get();

        validator.checkInventoryFull(user.getInventory().getItems().size());
        validator.checkItemInEquipment(user.getEquipment().getItems(), itemUnequip);

        user.getEquipment().getItems().remove(itemUnequip);
        user.getInventory().getItems().add(itemUnequip);
        user.swapItemToEquipmentOrInventory(itemUnequip, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userR.save(user);
        return new EquipOrUnequipDTO(user);
    }

    @Transactional
    public void upgradeItem(Long userId, Long itemId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of upgrading an item.
         * @param long userId
         * @param long itemId
         * @return void
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemId));
        validator.checkItemUpgradeInPossession(itemR.existsByIdAndUserId(itemId, userId));
        validator.checkItemIsUpgradeable(itemR.isItemUpgradeable(itemId, ItemConst.ENABLED_EQUIP, ItemConst.POTION_TYPE));

        int itemLevel = itemR.findItemLevelById(itemId);
        validator.checkItemLevelMax(itemLevel);

        List<Item> gemItems = itemR.findGemByUserId(userId, ItemConst.GEM_ITEM_LVL_NAME);
        int gemsNeeded = itemLevel + 1;
        validator.checkUserHaveAmountGem(gemItems.size(), gemsNeeded);

        User user = userR.findById(userId).get();
        Item itemUpgrade = itemR.findById(itemId).get();

        List<Item> itemsToRemove = gemItems.subList(0, gemsNeeded);
        itemsToRemove.forEach(user.getInventory().getItems()::remove);
        itemR.deleteAll(itemsToRemove);

        itemUpgrade.itemUpgrade();
        itemR.save(itemUpgrade);
        userR.save(user);
    }
}
