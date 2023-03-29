package com.gianca1994.heropathbackend.resources.item.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.dto.request.ItemDTO;

import java.util.Set;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of validating the data sent to the ItemService.
 */

public class ItemServiceValidator {

    public void userFound(boolean userExist) throws NotFound {
        if (!userExist) throw new NotFound(ItemConst.USER_NOT_FOUND);
    }

    public void itemFound(boolean itemExist) throws NotFound {
        if (!itemExist) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
    }

    public void itemExists(boolean itemExist) throws NotFound {
        if (itemExist) throw new NotFound(ItemConst.ALREADY_EXISTS);
    }

    public void checkDtoToSaveItem(ItemDTO newItem) throws BadReq {
        if (newItem.getName().isEmpty()) throw new BadReq(ItemConst.NAME_NOT_EMPTY);
        if (newItem.getType().isEmpty()) throw new BadReq(ItemConst.TYPE_NOT_EMPTY);
        if (newItem.getLvlMin() < 0) throw new BadReq(ItemConst.LVL_NOT_LESS_0);
        if (newItem.getPrice() < 0) throw new BadReq(ItemConst.PRICE_NOT_LESS_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 || newItem.getVitality() < 0 || newItem.getLuck() < 0)
            throw new BadReq(ItemConst.STATS_NOT_LESS_0);
        if (!ItemConst.ENABLED_ITEM_TYPE_SAVE.contains(newItem.getType()))
            throw new BadReq(ItemConst.CANT_EQUIP_MORE_ITEM + newItem.getType());
    }

    public void checkGoldEnough(long goldUser, int itemPrice) throws Conflict {
        if (goldUser < itemPrice) throw new Conflict(ItemConst.NOT_ENOUGH_GOLD);
    }

    public void checkInventoryFull(int inventorySize) throws Conflict {
        if (inventorySize >= SvConfig.MAX_ITEMS_INVENTORY) throw new Conflict(ItemConst.INVENTORY_FULL);
    }

    public void inventoryContainsItem(Set<Item> userInventory, Item item) throws Conflict {
        if (!userInventory.contains(item)) throw new Conflict(ItemConst.ITEM_NOT_INVENTORY);
    }

    public void checkItemClassEquip(String userClass, String itemClass) throws Conflict {
        if (!userClass.equals(itemClass) && !"none".equals(itemClass)) throw new Conflict(ItemConst.ITEM_NOT_FOR_CLASS);
    }

    public void checkItemLevelEquip(int userLevel, int itemLevel) throws Conflict {
        if (userLevel < itemLevel) throw new Conflict(ItemConst.ITEM_LEVEL_REQ + itemLevel);
    }

    public void checkItemEquipIfPermitted(String itemType) throws Conflict {
        if (!ItemConst.ENABLED_EQUIP.contains(itemType)) throw new Conflict(ItemConst.ITEM_EQUIP_NOT_PERMITTED);
    }

    public void checkEquipOnlyOneType(Set<Item> equipment, String itemType) throws Conflict {
        for (Item item : equipment) {
            if (item.getType().equals(itemType)) throw new Conflict(ItemConst.CANT_EQUIP_MORE_ITEM);
        }
    }

    public void checkItemInEquipment(Set<Item> equipment, Item item) {
        if (!equipment.contains(item)) throw new NotFound(ItemConst.ITEM_NOT_EQUIPMENT);
    }

    public void checkItemFromTrader(boolean itemFromTrader) throws Conflict {
        if (!itemFromTrader) throw new Conflict(ItemConst.ITEM_NOT_FROM_TRADER);
    }

    public void checkItemNotInPossession(boolean itemNotPossession) throws Conflict {
        if (itemNotPossession) throw new Conflict(ItemConst.ITEM_NOT_IN_POSSESSION);
    }

    public void checkItemUpgradeAmount(int upgradeAmount, int requirementAmount) throws BadReq {
        if (upgradeAmount < requirementAmount) throw new BadReq(ItemConst.NOT_ENOUGH_ITEMS_TO_UPGRADE);
    }

    public void checkItemLevelMax(int itemLevel) throws BadReq {
        if (itemLevel >= SvConfig.MAX_ITEM_LEVEL) throw new BadReq(ItemConst.ITEM_ALREADY_MAX_LVL);
    }

    public void checkItemUpgradeInPossession(boolean userHaveItem) throws Conflict {
        if (!userHaveItem) throw new Conflict(ItemConst.USER_NOT_HAVE_ITEM);
    }

    public void checkUserHaveAmountGem(int userGems, int gemsNeeded) throws Conflict {
        if (userGems < gemsNeeded) throw new Conflict(String.format(ItemConst.NOT_ENOUGH_GEMS, gemsNeeded));
    }

    public void checkItemIsUpgradeable(boolean isUpgradeable) throws Conflict {
        if (!isUpgradeable) throw new Conflict(ItemConst.ITEM_NOT_UPGRADEABLE);
    }
}