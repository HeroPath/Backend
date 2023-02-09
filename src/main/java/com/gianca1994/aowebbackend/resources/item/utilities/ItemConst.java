package com.gianca1994.aowebbackend.resources.item.utilities;

import java.util.Arrays;
import java.util.List;

public class ItemConst {
    /**
     * @Author: Gianca1994
     * Explanation: This class contains all the constants used in the Item class
     */
    public static final String POTION_NAME = "potion";
    public static final String ITEM_NOT_FOUND = "Item not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String ALREADY_EXISTS = "Item already exists";
    public static final String NAME_NOT_EMPTY = "Name cannot be empty";
    public static final String TYPE_NOT_EMPTY = "Type cannot be empty";
    public static final String LVL_NOT_LESS_0 = "LvlMin cannot be less than 0";
    public static final String PRICE_NOT_LESS_0 = "Price cannot be less than 0";
    public static final String STATS_NOT_LESS_0 = "Stats cannot be less than 0";
    public static final List<String> ENABLED_EQUIP = Arrays.asList(
            "weapon",
            "shield",
            "helmet",
            "armor",
            "pants",
            "gloves",
            "boots",
            "ship",
            "wings",
            "potion"
    );
    public static final String CANT_EQUIP_MORE_ITEM = "You can't equip more than one %s item";
    public static final String NOT_ENOUGH_GOLD = "You don't have enough gold";
    public static final String INVENTORY_FULL = "Inventory is full";
    public static final String ITEM_NOT_INVENTORY = "Item not found in inventory";
    public static final String ITEM_NOT_FOR_CLASS = "The item does not correspond to your class";
    public static final String CANT_EQUIP_SAME_TYPE = "You can't equip two items of the same type";
    public static final String ITEM_LEVEL_REQ = "You can't equip an item that requires level ";
    public static final String ITEM_NOT_IN_EQUIPMENT = "Item not found in equipment";
}