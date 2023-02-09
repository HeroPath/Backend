package com.gianca1994.aowebbackend.resources.item.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.List;
import java.util.Set;

public class ItemServiceValidator {

    public void userFound(boolean userExist) throws NotFound {
        /**
         *
         */
        if (!userExist) throw new NotFound(ItemConst.USER_NOT_FOUND);
    }

    public void itemFound(boolean itemExist) throws NotFound {
        /**
         *
         */
        if (!itemExist) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
    }

    public void itemExists(boolean itemExist) throws NotFound {
        /**
         *
         */
        if (itemExist) throw new NotFound(ItemConst.ALREADY_EXISTS);
    }

    public void checkDtoToSaveItem(ItemDTO newItem) throws BadRequest {
        /**
         *
         */
        if (newItem.getName().isEmpty()) throw new BadRequest(ItemConst.NAME_NOT_EMPTY);
        if (newItem.getType().isEmpty()) throw new BadRequest(ItemConst.TYPE_NOT_EMPTY);
        if (newItem.getLvlMin() <= 0) throw new BadRequest(ItemConst.LVL_NOT_LESS_0);
        if (newItem.getPrice() < 0) throw new BadRequest(ItemConst.PRICE_NOT_LESS_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 || newItem.getVitality() < 0 || newItem.getLuck() < 0)
            throw new BadRequest(ItemConst.STATS_NOT_LESS_0);
        if (!ItemConst.ENABLED_EQUIP.contains(newItem.getType()))
            throw new BadRequest(ItemConst.CANT_EQUIP_MORE_ITEM + newItem.getType());
    }

    public void checkGoldEnough(long goldUser, int itemPrice) throws Conflict {
        /**
         *
         */
        if (goldUser < itemPrice) throw new Conflict(ItemConst.NOT_ENOUGH_GOLD);
    }

    public void checkInventoryFull(int inventorySize) throws Conflict {
        /**
         *
         */
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new Conflict(ItemConst.INVENTORY_FULL);
    }

    public void inventoryContainsItem(List<Item> userInventory, Item item) throws Conflict {
        /**
         *
         */
        if (!userInventory.contains(item)) throw new Conflict(ItemConst.ITEM_NOT_INVENTORY);
    }

    public void checkItemClassEquip(String userClass, String itemClass) throws Conflict {
        /**
         *
         */
        if (!userClass.equals(itemClass) && !"none".equals(userClass)) throw new Conflict(ItemConst.ITEM_NOT_FOR_CLASS);
    }

    public void checkItemLevelEquip(int userLevel, int itemLevel) throws Conflict {
        /**
         *
         */
        if (userLevel < itemLevel) throw new Conflict(ItemConst.ITEM_LEVEL_REQ + itemLevel);
    }

    public void checkItemEquipIfPermitted(String itemType) throws Conflict {
        /**
         *
         */
        if (!ItemConst.ENABLED_EQUIP.contains(itemType)) throw new Conflict(ItemConst.ITEM_EQUIP_NOT_PERMITTED);
    }

    public void checkEquipOnlyOneType(Set<Item> equipment, String itemType) throws Conflict {
        /**
         *
         */
        for (Item item : equipment) {
            if (item.getType().equals(itemType)) throw new Conflict(ItemConst.CANT_EQUIP_MORE_ITEM);
        }
    }

    public void checkItemInEquipment(Set<Item> equipment, Item item) {
        /**
         *
         */
        if (!equipment.contains(item)) throw new NotFound(ItemConst.ITEM_NOT_EQUIPMENT);
    }
}