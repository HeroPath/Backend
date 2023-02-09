package com.gianca1994.aowebbackend.resources.item.utilities;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.user.User;

import java.util.Objects;

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

    ////////////////////////////////////////////////////////////////////////////////
    public void sellItem(User user, Item itemSell) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the request to sell an item
         * @param User user
         * @param Item itemSell
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.isNull(itemSell)) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
        if (!user.getInventory().getItems().contains(itemSell))
            throw new NotFound(ItemConst.ITEM_NOT_INVENTORY);
    }

    public void equipItem(User user, Item itemEquip) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the request to equip an item
         * @param User user
         * @param Item itemEquip
         * @return void
         */
        if (itemEquip == null) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (!user.getInventory().getItems().contains(itemEquip))
            throw new NotFound(ItemConst.ITEM_NOT_INVENTORY);
        if (!Objects.equals(user.getAClass(), itemEquip.getClassRequired()) && !Objects.equals(itemEquip.getClassRequired(), "none"))
            throw new Conflict(ItemConst.ITEM_NOT_FOR_CLASS);

        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!ItemConst.ENABLED_EQUIP.contains(itemEquipedOld.getType()))
                throw new Conflict(ItemConst.CANT_EQUIP_MORE_ITEM + itemEquipedOld.getType());
            if (Objects.equals(itemEquipedOld.getType(), itemEquip.getType()))
                throw new Conflict(ItemConst.CANT_EQUIP_SAME_TYPE);
        }

        if (user.getLevel() < itemEquip.getLvlMin())
            throw new Conflict(ItemConst.ITEM_LEVEL_REQ + itemEquip.getLvlMin());
    }

    public void unequipItem(User user, Item itemUnequip) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the request to unequip an item
         * @param User user
         * @param Item itemUnequip
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (itemUnequip == null) throw new NotFound(ItemConst.ITEM_NOT_FOUND);

        if (!user.getEquipment().getItems().contains(itemUnequip))
            throw new NotFound(ItemConst.ITEM_NOT_EQUIPMENT);
        if (user.getInventory().getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY)
            throw new Conflict(ItemConst.INVENTORY_FULL);
    }
}