package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.request.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.dto.response.UserEquipOrUnequipDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    ItemServiceValidator validator = new ItemServiceValidator();

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Item> getClassShop(String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        return itemRepository.findByClassRequiredOrderByLvlMinAsc(aClass);
    }

    public void saveItem(ItemDTO newItem) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        Item item = itemRepository.findByName(newItem.getName().toLowerCase());
        validator.saveItem(item, newItem);

        String classRequired = newItem.getClassRequired().equals("") ? "none" : newItem.getClassRequired();
        itemRepository.save(new Item(
                newItem.getName().toLowerCase(), newItem.getType(), newItem.getLvlMin(),
                classRequired, newItem.getPrice(),
                newItem.getStrength(), newItem.getDexterity(), newItem.getIntelligence(),
                newItem.getVitality(), newItem.getLuck()
        ));
    }

    public User buyItem(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String username
         * @param String name
         * @return none
         */
        User user = userRepository.findByUsername(username);
        Item itemBuy = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        validator.buyItem(user, itemBuy);

        user.getInventory().getItems().add(itemBuy);
        user.setGold(user.getGold() - itemBuy.getPrice());
        userRepository.save(user);
        return user;
    }

    public User sellItem(String username, NameRequestDTO nameRequestDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String username
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        User user = userRepository.findByUsername(username);
        Item itemSell = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        validator.sellItem(user, itemSell);

        user.setGold(user.getGold() + (itemSell.getPrice() / 2));
        user.getInventory().getItems().remove(itemSell);
        userRepository.save(user);
        return user;
    }

    public UserEquipOrUnequipDTO equipItem(String username,
                                           EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(username);
        Item itemEquip = itemRepository.findById(equipUnequipItemDTO.getId()).get();
        validator.equipItem(user, itemEquip);

        user.getInventory().getItems().remove(itemEquip);
        if (Objects.equals(itemEquip.getType(), ItemConst.POTION_NAME)) {
            user.setHp(user.getMaxHp());
        } else {
            user.getEquipment().getItems().add(itemEquip);
            user.swapItemToEquipmentOrInventory(itemEquip, true);
        }
        userRepository.save(user);
        return new UserEquipOrUnequipDTO(user);
    }

    public UserEquipOrUnequipDTO unequipItem(String username, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(username);
        Item itemUnequip = itemRepository.findById(equipUnequipItemDTO.getId()).get();
        validator.unequipItem(user, itemUnequip);

        user.getEquipment().getItems().remove(itemUnequip);
        user.getInventory().getItems().add(itemUnequip);
        user.swapItemToEquipmentOrInventory(itemUnequip, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userRepository.save(user);

        return new UserEquipOrUnequipDTO(user);
    }

}
