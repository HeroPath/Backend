package com.gianca1994.aowebbackend.resources.item.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;

import java.util.List;
import java.util.Set;

/**
 * @Author: Gianca1994
 * Explanation: This class is in charge of validating the data sent to the ItemService.
 */

public class ItemServiceValidator {

    public void userFound(boolean userExist) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of checking if a user exists.
         * @param boolean userExist
         * @return void
         */
        if (!userExist) throw new NotFound(ItemConst.USER_NOT_FOUND);
    }

    public void itemFound(boolean itemExist) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of checking if an item exists.
         * @param boolean itemExist
         * @return void
         */
        if (!itemExist) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
    }

    public void itemExists(boolean itemExist) throws NotFound {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of checking if an item already exists.
         * @param boolean itemExist
         * @return void
         */
        if (itemExist) throw new NotFound(ItemConst.ALREADY_EXISTS);
    }

    public void checkDtoToSaveItem(ItemDTO newItem) throws BadRequest {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of checking if the data sent to save an item is correct.
         * @param ItemDTO newItem
         * @return void
         */
        if (newItem.getName().isEmpty()) throw new BadRequest(ItemConst.NAME_NOT_EMPTY);
        if (newItem.getType().isEmpty()) throw new BadRequest(ItemConst.TYPE_NOT_EMPTY);
        if (newItem.getLvlMin() <= 0) throw new BadRequest(ItemConst.LVL_NOT_LESS_0);
        if (newItem.getPrice() < 0) throw new BadRequest(ItemConst.PRICE_NOT_LESS_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 || newItem.getVitality() < 0 || newItem.getLuck() < 0)
            throw new BadRequest(ItemConst.STATS_NOT_LESS_0);
        if (!ItemConst.ENABLED_ITEM_TYPE_SAVE.contains(newItem.getType()))
            throw new BadRequest(ItemConst.CANT_EQUIP_MORE_ITEM + newItem.getType());
    }

    public void checkGoldEnough(long goldUser, int itemPrice) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user have enough gold to buy the item.
         * @param long goldUser
         * @param int itemPrice
         * @return void
         */
        if (goldUser < itemPrice) throw new Conflict(ItemConst.NOT_ENOUGH_GOLD);
    }

    public void checkInventoryFull(int inventorySize) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user inventory is full.
         * @param int inventorySize
         * @return void
         */
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new Conflict(ItemConst.INVENTORY_FULL);
    }

    public void inventoryContainsItem(List<Item> userInventory, Item item) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user inventory contains the item.
         * @param List<Item> userInventory
         * @param Item item
         * @return void
         */
        if (!userInventory.contains(item)) throw new Conflict(ItemConst.ITEM_NOT_INVENTORY);
    }

    public void checkItemClassEquip(String userClass, String itemClass) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user class is the same of the item class.
         * @param String userClass
         * @param String itemClass
         * @return void
         */
        if (!userClass.equals(itemClass) && !"none".equals(itemClass)) throw new Conflict(ItemConst.ITEM_NOT_FOR_CLASS);
    }

    public void checkItemLevelEquip(int userLevel, int itemLevel) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user level is enough to equip the item.
         * @param int userLevel
         * @param int itemLevel
         * @return void
         */
        if (userLevel < itemLevel) throw new Conflict(ItemConst.ITEM_LEVEL_REQ + itemLevel);
    }

    public void checkItemEquipIfPermitted(String itemType) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the item can be equipped.
         * @param String itemType
         * @return void
         */
        if (!ItemConst.ENABLED_EQUIP.contains(itemType)) throw new Conflict(ItemConst.ITEM_EQUIP_NOT_PERMITTED);
    }

    public void checkEquipOnlyOneType(Set<Item> equipment, String itemType) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the user can equip more items of the same type.
         * @param Set<Item> equipment
         * @param String itemType
         * @return void
         */
        for (Item item : equipment) {
            if (item.getType().equals(itemType)) throw new Conflict(ItemConst.CANT_EQUIP_MORE_ITEM);
        }
    }

    public void checkItemInEquipment(Set<Item> equipment, Item item) {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the item is in the equipment of the user.
         * @param Set<Item> equipment
         * @param Item item
         * @return void
         */
        if (!equipment.contains(item)) throw new NotFound(ItemConst.ITEM_NOT_EQUIPMENT);
    }

    public void checkItemFromTrader(boolean itemFromTrader) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the item is from the trader.
         * @param boolean itemFromTrader
         * @return void
         */
        if (!itemFromTrader) throw new Conflict(ItemConst.ITEM_NOT_FROM_TRADER);
    }

    public void checkItemNotInPossession(boolean itemNotPossession) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method check if the item is not in the possession of the user.
         * @param boolean itemNotPossession
         * @return void
         */
        if (itemNotPossession) throw new Conflict(ItemConst.ITEM_NOT_IN_POSSESSION);
    }

    public void checkItemUpgradeAmount(int upgradeAmount, int requirementAmount) throws BadRequest {
        /**
         *
         */
        if (upgradeAmount < requirementAmount) throw new BadRequest(ItemConst.NOT_ENOUGH_ITEMS_TO_UPGRADE);
    }
}