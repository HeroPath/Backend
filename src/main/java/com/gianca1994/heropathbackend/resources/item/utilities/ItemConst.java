package com.gianca1994.heropathbackend.resources.item.utilities;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This class contains all the constants used in the Item class
 */

public class ItemConst {
    public static final String POTION_TYPE = "potion";
    public static final String GEM_ITEM_LVL_NAME = "progress gem";
    public static final List<String> ITEM_NOT_LEVEL_AND_QUALITY = Arrays.asList(
            "potion", "gem"
    );
    public static final List<String> ENABLED_EQUIP = Arrays.asList(
            "weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings", "potion"
    );
    public static final String ITEM_EQUIP_NOT_PERMITTED = "You can't equip this item";
    public static final String CANT_EQUIP_MORE_ITEM = "You can't equip two items of the same type";
    public static final String ITEM_NOT_EQUIPMENT = "Item not found in equipment";
    public static final String ITEM_NOT_FOR_CLASS = "The item does not correspond to your class";
    public static final String ITEM_LEVEL_REQ = "You can't equip an item that requires level ";
    public static final String ITEM_NOT_FROM_TRADER = "You can only buy items that come from the trader.";
    public static final String ITEM_NOT_IN_POSSESSION = "You can't sell an item you don't have in your possession.";
    public static final String NOT_ENOUGH_ITEMS_TO_UPGRADE = "You don't have enough items to upgrade";
    public static final String ITEM_ALREADY_MAX_LVL = "Item already at max level";
    public static final String USER_NOT_HAVE_ITEM = "You don't have this item";
    public static final String NOT_ENOUGH_GEMS = "You don't have enough gems, you need: %d gems to upgrade the item";
    public static final String ITEM_NOT_UPGRADEABLE = "You can't upgrade this item";
}