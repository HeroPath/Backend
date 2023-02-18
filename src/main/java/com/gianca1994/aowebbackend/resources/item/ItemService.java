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
import java.util.Objects;

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
        return itemR.findByClassRequiredAndUserIsNullOrderByLvlMinAsc(aClass);
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
                newItem.getStrength(), newItem.getDexterity(), newItem.getIntelligence(), newItem.getVitality(), newItem.getLuck()
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

        User user = userR.getReferenceById(userId);
        Item itemBuy = itemR.getReferenceById(itemBuyId);

        validator.checkInventoryFull(user.getInventory().getItems().size());
        validator.checkGoldEnough(user.getGold(), itemBuy.getPrice());

        user.setGold(user.getGold() - itemBuy.getPrice());

        Item newItemBuy = new Item(
                itemBuy.getName(), itemBuy.getType(), itemBuy.getLvlMin(), itemBuy.getPrice() / 2, itemBuy.getClassRequired(),
                itemBuy.getQuality(), itemBuy.getItemLevel(), itemBuy.getStrength(), itemBuy.getDexterity(), itemBuy.getIntelligence(),
                itemBuy.getVitality(), itemBuy.getLuck(), user
        );
        itemR.save(newItemBuy);

        user.getInventory().getItems().add(newItemBuy);
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

        User user = userR.getReferenceById(userId);
        Item itemSell = itemR.getReferenceById(itemSellId);
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

        User user = userR.getReferenceById(userId);
        Item itemEquip = itemR.findById(itemId).get();

        validator.checkItemEquipIfPermitted(itemEquip.getType());
        validator.checkEquipOnlyOneType(user.getEquipment().getItems(), itemEquip.getType());
        validator.inventoryContainsItem(user.getInventory().getItems(), itemEquip);
        validator.checkItemClassEquip(user.getAClass(), itemEquip.getClassRequired());
        validator.checkItemLevelEquip(user.getLevel(), itemEquip.getLvlMin());

        user.getInventory().getItems().remove(itemEquip);
        if (Objects.equals(itemEquip.getType(), ItemConst.POTION_NAME)) {
            user.setHp(user.getMaxHp());
            itemR.delete(itemEquip);
        } else {
            user.getEquipment().getItems().add(itemEquip);
            user.swapItemToEquipmentOrInventory(itemEquip, true);
        }
        userR.save(user);
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

        User user = userR.getReferenceById(userId);
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
        if (itemR.existsByIdAndUserId(itemId, userId)) throw new Conflict("Item not in inventory");

        int itemLevel = itemR.findItemLevelById(itemId);
        validator.checkItemLevelMax(itemLevel);


        List<Item> gemItems = itemR.findGemByUserId(userId, ItemConst.GEM_ITEM_LVL_NAME);
        int gemsNeeded = itemLevel + 1;
        if (gemItems.size() < gemsNeeded) throw new Conflict("You don't have the required gem");


        User user = userR.getReferenceById(userId);
        Item itemUpgrade = itemR.findById(itemId).get();

        if (!ItemConst.ENABLED_EQUIP.contains(itemUpgrade.getType()) && !itemUpgrade.getType().equals(ItemConst.POTION_NAME))
            throw new Conflict("Item not upgradable");



        List<Item> itemsToRemove = gemItems.subList(0, gemsNeeded);
        user.getInventory().getItems().removeAll(itemsToRemove);
        itemR.deleteAll(itemsToRemove);

        itemUpgrade.setItemLevel(itemUpgrade.getItemLevel() + 1);
        itemUpgrade.setStrength(itemUpgrade.getStrength() + 5);
        itemUpgrade.setDexterity(itemUpgrade.getDexterity() + 5);
        itemUpgrade.setIntelligence(itemUpgrade.getIntelligence() + 5);
        itemUpgrade.setVitality(itemUpgrade.getVitality() + 5);
        itemUpgrade.setLuck(itemUpgrade.getLuck() + 5);
        itemR.save(itemUpgrade);
        userR.save(user);
    }
}
