package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.BuySellDTO;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemConst;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemServiceValidator;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.item.dto.response.EquipOrUnequipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        if (!itemR.isUserIdNull(itemBuyId)) throw new Conflict("You can only buy items that come from the trader.");

        User user = userR.getReferenceById(userId);
        Item itemBuy = itemR.getReferenceById(itemBuyId);

        validator.checkGoldEnough(user.getGold(), itemBuy.getPrice());
        validator.checkInventoryFull(user.getInventory().getItems().size());

        Item newItemBuy = new Item(
                itemBuy.getName(), itemBuy.getType(), itemBuy.getLvlMin(), itemBuy.getPrice() / 2, itemBuy.getClassRequired(),
                itemBuy.getStrength(), itemBuy.getDexterity(), itemBuy.getIntelligence(),
                itemBuy.getVitality(), itemBuy.getLuck(), user
        );

        user.getInventory().getItems().add(newItemBuy);
        user.setGold(user.getGold() - itemBuy.getPrice());
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
        if (itemR.hasItem(userId, itemSellId))
            throw new Conflict("You can only sell items that you have bought from the trader.");

        User user = userR.getReferenceById(userId);
        Item itemSell = itemR.getReferenceById(itemSellId);
        validator.inventoryContainsItem(user.getInventory().getItems(), itemSell);

        user.setGold(user.getGold() + (itemSell.getPrice()));
        user.getInventory().getItems().remove(itemSell);
        itemR.delete(itemSell);
        userR.save(user);
        return new BuySellDTO(user.getGold(), user.getInventory());
    }

    public EquipOrUnequipDTO equipItem(Long userId, long itemId) throws Conflict {
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
        Item itemEquip = itemR.getReferenceById(itemId);

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

    public EquipOrUnequipDTO unequipItem(Long userId, long itemId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of unequipping an item.
         * @param String username
         * @param long itemId
         * @return EquipOrUnequipDTO
         */
        validator.userFound(userR.existsById(userId));
        validator.itemFound(itemR.existsById(itemId));

        User user = userR.getReferenceById(userId);
        Item itemUnequip = itemR.getReferenceById(itemId);

        validator.checkInventoryFull(user.getInventory().getItems().size());
        validator.checkItemInEquipment(user.getEquipment().getItems(), itemUnequip);

        user.getEquipment().getItems().remove(itemUnequip);
        user.getInventory().getItems().add(itemUnequip);
        user.swapItemToEquipmentOrInventory(itemUnequip, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userR.save(user);

        return new EquipOrUnequipDTO(user);
    }

}
