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

    public void saveItem(Item item, ItemDTO newItem) throws Conflict, BadRequest {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the request to save an item
         * @param Item item
         * @param ItemDTO newItem
         * @return void
         */
        if (item != null) throw new Conflict(ItemConst.ITEM_ALREADY_EXISTS);

        if (Objects.equals(newItem.getName(), "")) throw new BadRequest(ItemConst.NAME_CANNOT_BE_EMPTY);
        if (Objects.equals(newItem.getType(), "")) throw new BadRequest(ItemConst.TYPE_CANNOT_BE_EMPTY);
        if (newItem.getLvlMin() <= 0) throw new BadRequest(ItemConst.LVL_MIN_CANNOT_BE_LESS_THAN_0);
        if (newItem.getPrice() < 0) throw new BadRequest(ItemConst.PRICE_CANNOT_BE_LESS_THAN_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 ||
                newItem.getIntelligence() < 0 || newItem.getVitality() < 0 ||
                newItem.getLuck() < 0) throw new BadRequest(ItemConst.STATS_CANNOT_BE_LESS_THAN_0);
        if (!ItemConst.ITEM_ENABLED_TO_EQUIP.contains(newItem.getType()))
            throw new Conflict(ItemConst.YOU_CANT_EQUIP_MORE_THAN_ONE + newItem.getType());
    }

    public void buyItem(User user, Item itemBuy) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to validate the request to buy an item
         * @param User user
         * @param Item itemBuy
         * @return void
         */
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);
        if (Objects.isNull(itemBuy)) throw new NotFound(ItemConst.ITEM_NOT_FOUND);
        if (user.getGold() < itemBuy.getPrice()) throw new Conflict(ItemConst.YOU_DONT_HAVE_ENOUGH_GOLD);
        if (user.getInventory().getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY)
            throw new Conflict(ItemConst.INVENTORY_IS_FULL);
    }

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
            throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_INVENTORY);
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
            throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_INVENTORY);
        if (!Objects.equals(user.getAClass(), itemEquip.getClassRequired()) && !Objects.equals(itemEquip.getClassRequired(), "none"))
            throw new Conflict(ItemConst.ITEM_DOES_NOT_CORRESPOND_TO_YOUR_CLASS);

        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!ItemConst.ITEM_ENABLED_TO_EQUIP.contains(itemEquipedOld.getType()))
                throw new Conflict(ItemConst.YOU_CANT_EQUIP_MORE_THAN_ONE + itemEquipedOld.getType());
            if (Objects.equals(itemEquipedOld.getType(), itemEquip.getType()))
                throw new Conflict(ItemConst.YOU_CANT_EQUIP_TWO_ITEMS_OF_THE_SAME_TYPE);
        }

        if (user.getLevel() < itemEquip.getLvlMin())
            throw new Conflict(ItemConst.YOU_CANT_EQUIP_AN_ITEM_THAT_REQUIRES_LEVEL + itemEquip.getLvlMin());
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
            throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_EQUIPMENT);
        if (user.getInventory().getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY)
            throw new Conflict(ItemConst.INVENTORY_IS_FULL);
    }
}