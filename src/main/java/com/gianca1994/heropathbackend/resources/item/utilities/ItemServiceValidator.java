package com.gianca1994.heropathbackend.resources.item.utilities;

import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.exception.BadReq;
import com.gianca1994.heropathbackend.exception.Conflict;
import com.gianca1994.heropathbackend.exception.NotFound;
import com.gianca1994.heropathbackend.resources.item.Item;
import com.gianca1994.heropathbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.heropathbackend.utils.Const;

import java.util.Set;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of validating the data sent to the ItemService.
 */

public class ItemServiceValidator {

    public void userExist(boolean exist) throws NotFound {
        if (!exist) throw new NotFound(Const.USER.NOT_FOUND.getMsg());
    }

    public void itemExist(boolean exist) throws NotFound {
        if (!exist) throw new NotFound(Const.ITEM.NOT_FOUND.getMsg());
    }

    public void itemAlreadyExist(boolean itemExist) throws NotFound {
        if (itemExist) throw new NotFound(Const.ITEM.ALREADY_EXIST.getMsg());
    }

    public void dtoSaveItem(ItemDTO newItem) throws BadReq {
        if (newItem.getName().isEmpty()) throw new BadReq(Const.ITEM.NAME_EMPTY.getMsg());
        if (newItem.getType().isEmpty()) throw new BadReq(Const.ITEM.TYPE_EMPTY.getMsg());
        if (newItem.getLvlMin() < 0) throw new BadReq(Const.ITEM.LVL_LESS_0.getMsg());
        if (newItem.getPrice() < 0) throw new BadReq(Const.ITEM.PRICE_LESS_0.getMsg());
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 || newItem.getVitality() < 0 || newItem.getLuck() < 0)
            throw new BadReq(Const.ITEM.STATS_LESS_0.getMsg());
        if (!Const.ITEM.ENABLED_ITEM_TYPE_SAVE.getList().contains(newItem.getType()))
            throw new BadReq(Const.ITEM.CANT_EQUIP_MORE_ITEM.getMsg() + newItem.getType());
    }

    public void goldEnough(long gold, int price) throws Conflict {
        if (gold < price) throw new Conflict(Const.ITEM.NOT_ENOUGH_GOLD.getMsg());
    }

    public void inventoryFull(int size) throws Conflict {
        if (size >= SvConfig.SLOTS_INVENTORY) throw new Conflict(Const.ITEM.INVENTORY_FULL.getMsg());
    }

    public void inventoryContainsItem(Set<Item> userInv, Item item) throws Conflict {
        if (!userInv.contains(item)) throw new Conflict(Const.ITEM.NOT_IN_INVENTORY.getMsg());
    }

    public void itemClassEquip(String userClass, String itemClass) throws Conflict {
        if (!userClass.equals(itemClass) && !"none".equals(itemClass))
            throw new Conflict(Const.ITEM.ITEM_NOT_FOR_CLASS.getMsg());
    }

    public void itemLevelEquip(int userLevel, int itemLevel) throws Conflict {
        if (userLevel < itemLevel) throw new Conflict(Const.ITEM.ITEM_LEVEL_REQ.getMsg() + itemLevel);
    }

    public void itemEquipIfPermitted(String itemType) throws Conflict {
        if (!Const.ITEM.ENABLED_EQUIP.getList().contains(itemType))
            throw new Conflict(Const.ITEM.EQUIP_NOT_PERMITTED.getMsg());
    }

    public void equipOnlyOneType(Set<Item> equipment, String itemType) throws Conflict {
        for (Item item : equipment) {
            if (item.getType().equals(itemType)) throw new Conflict(Const.ITEM.CANT_EQUIP_MORE_ITEM.getMsg());
        }
    }

    public void itemInEquipment(Set<Item> equipment, Item item) {
        if (!equipment.contains(item)) throw new NotFound(Const.ITEM.NOT_IN_EQUIPMENT.getMsg());
    }

    public void itemFromTrader(boolean fromTrader) throws Conflict {
        if (!fromTrader) throw new Conflict(Const.ITEM.NOT_FROM_TRADER.getMsg());
    }

    public void itemInPossession(boolean itemNotPossession) throws Conflict {
        if (itemNotPossession) throw new Conflict(Const.ITEM.NOT_IN_POSSESSION.getMsg());
    }

    public void itemLevelMax(int lvl) throws BadReq {
        if (lvl >= SvConfig.MAX_ITEM_LEVEL) throw new BadReq(Const.ITEM.ALREADY_MAX_LVL.getMsg());
    }

    public void itemUpgradeInPossession(boolean haveItem) throws Conflict {
        if (!haveItem) throw new Conflict(Const.ITEM.NOT_HAVE_ITEM.getMsg());
    }

    public void hasEnoughGems(int userGems, int needed) throws Conflict {
        if (userGems < needed) throw new Conflict(String.format(Const.ITEM.NOT_ENOUGH_GEMS.getMsg(), needed));
    }

    public void itemIsUpgradeable(boolean isUpgradeable) throws Conflict {
        if (!isUpgradeable) throw new Conflict(Const.ITEM.NOT_UPGRADEABLE.getMsg());
    }
}