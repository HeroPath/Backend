package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.item.dto.request.EquipUnequipItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.request.ItemDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.BuySellDTO;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemConst;
import com.gianca1994.aowebbackend.resources.item.utilities.ItemServiceValidator;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.item.dto.response.EquipOrUnequipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return itemR.findByClassRequiredOrderByLvlMinAsc(aClass);
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
                newItem.getName().toLowerCase(), newItem.getType(), newItem.getLvlMin(),
                newItem.getClassRequired().equals("") ? "none" : newItem.getClassRequired(),
                newItem.getPrice(),
                newItem.getStrength(), newItem.getDexterity(), newItem.getIntelligence(),
                newItem.getVitality(), newItem.getLuck()
        ));
    }

    public BuySellDTO buyItem(String username, String itemName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String username
         * @param String name
         * @return none
         */
        validator.userFound(userR.existsByUsername(username));
        validator.itemFound(itemR.existsByName(itemName));

        User user = userR.findByUsername(username);
        Item itemBuy = itemR.findByName(itemName);
        validator.checkGoldEnough(user.getGold(), itemBuy.getPrice());
        validator.checkInventoryFull(user.getInventory().getItems().size());

        user.getInventory().getItems().add(itemBuy);
        user.setGold(user.getGold() - itemBuy.getPrice());
        userR.save(user);
        return new BuySellDTO(user.getGold(), user.getInventory());
    }

    public BuySellDTO sellItem(String username, String itemName) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String username
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        validator.userFound(userR.existsByUsername(username));
        validator.itemFound(itemR.existsByName(itemName));

        User user = userR.findByUsername(username);
        Item itemSell = itemR.findByName(itemName);
        validator.inventoryContainsItem(user.getInventory().getItems(), itemSell);

        user.setGold(user.getGold() + (itemSell.getPrice() / 2));
        user.getInventory().getItems().remove(itemSell);
        userR.save(user);
        return new BuySellDTO(user.getGold(), user.getInventory());
    }

    public EquipOrUnequipDTO equipItem(String username, long itemId) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        validator.userFound(userR.existsByUsername(username));
        validator.itemFound(itemR.existsById(itemId));

        User user = userR.findByUsername(username);
        Item itemEquip = itemR.findById(itemId).get();
        validator.inventoryContainsItem(user.getInventory().getItems(), itemEquip);
        validator.checkItemClassEquip(user.getAClass(), itemEquip.getClassRequired());
        validator.checkItemLevelEquip(user.getLevel(), itemEquip.getLvlMin());


        validator.equipItem(user, itemEquip);

        user.getInventory().getItems().remove(itemEquip);
        if (Objects.equals(itemEquip.getType(), ItemConst.POTION_NAME)) user.setHp(user.getMaxHp());
        else {
            user.getEquipment().getItems().add(itemEquip);
            user.swapItemToEquipmentOrInventory(itemEquip, true);
        }
        userR.save(user);
        return new EquipOrUnequipDTO(user);
    }

    public EquipOrUnequipDTO unequipItem(String username, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userR.findByUsername(username);
        Item itemUnequip = itemR.findById(equipUnequipItemDTO.getId()).get();
        validator.unequipItem(user, itemUnequip);

        user.getEquipment().getItems().remove(itemUnequip);
        user.getInventory().getItems().add(itemUnequip);
        user.swapItemToEquipmentOrInventory(itemUnequip, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userR.save(user);

        return new EquipOrUnequipDTO(user);
    }

}
