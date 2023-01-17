package com.gianca1994.aowebbackend.resources.item;

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
    public static final String ITEM_ALREADY_EXISTS = "Item already exists";
    public static final String NAME_CANNOT_BE_EMPTY = "Name cannot be empty";
    public static final String TYPE_CANNOT_BE_EMPTY = "Type cannot be empty";
    public static final String LVL_MIN_CANNOT_BE_LESS_THAN_0 = "LvlMin cannot be less than 0";
    public static final String PRICE_CANNOT_BE_LESS_THAN_0 = "Price cannot be less than 0";
    public static final String STATS_CANNOT_BE_LESS_THAN_0 = "Stats cannot be less than 0";
    public static final List<String> ITEM_ENABLED_TO_EQUIP = Arrays.asList(
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
    public static final String YOU_CANT_EQUIP_MORE_THAN_ONE = "You can't equip more than one ";
    public static final String YOU_DONT_HAVE_ENOUGH_GOLD = "You don't have enough gold";
    public static final String INVENTORY_IS_FULL = "Inventory is full";
    public static final String ITEM_NOT_FOUND_IN_INVENTORY = "Item not found in inventory";
    public static final String ITEM_DOES_NOT_CORRESPOND_TO_YOUR_CLASS = "The item does not correspond to your class";
    public static final String YOU_CANT_EQUIP_TWO_ITEMS_OF_THE_SAME_TYPE = "You can't equip two items of the same type";
    public static final String YOU_CANT_EQUIP_AN_ITEM_THAT_REQUIRES_LEVEL = "You can't equip an item that requires level ";
    public static final String ITEM_NOT_FOUND_IN_EQUIPMENT = "Item not found in equipment";
}