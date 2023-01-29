package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.UserService;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

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
        List<Item> items = itemRepository.findAll();
        items.removeIf(item -> !item.getClassRequired().equals(aClass));
        if (items.isEmpty()) throw new NotFound("No items found for class " + aClass);

        items.sort(Comparator.comparing(Item::getLvlMin));
        return items;
    }

    public Item saveItem(ItemDTO newItem) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        Item item = itemRepository.findByName(newItem.getName().toLowerCase());
        if (item != null) throw new Conflict(ItemConst.ITEM_ALREADY_EXISTS);

        if (Objects.equals(newItem.getName(), "")) throw new BadRequest(ItemConst.NAME_CANNOT_BE_EMPTY);
        if (Objects.equals(newItem.getType(), "")) throw new BadRequest(ItemConst.TYPE_CANNOT_BE_EMPTY);
        if (newItem.getLvlMin() <= 0) throw new BadRequest(ItemConst.LVL_MIN_CANNOT_BE_LESS_THAN_0);
        if (newItem.getPrice() < 0) throw new BadRequest(ItemConst.PRICE_CANNOT_BE_LESS_THAN_0);
        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 ||
                newItem.getIntelligence() < 0 || newItem.getVitality() < 0 ||
                newItem.getLuck() < 0) throw new BadRequest(ItemConst.STATS_CANNOT_BE_LESS_THAN_0);
        if (!ItemConst.ITEM_ENABLED_TO_EQUIP.contains(newItem.getType()))
            throw new Conflict(ItemConst.YOU_CANT_EQUIP_MORE_THAN_ONE + newItem.getType() + " item");
        if (Objects.equals(newItem.getClassRequired(), "")) newItem.setClassRequired("none");

        return itemRepository.save(new Item(
                newItem.getName().toLowerCase(),
                newItem.getType(),
                newItem.getLvlMin(),
                newItem.getClassRequired(),
                newItem.getPrice(),
                newItem.getStrength(),
                newItem.getDexterity(),
                newItem.getIntelligence(),
                newItem.getVitality(),
                newItem.getLuck())
        );
    }

    public void buyItem(String username, NameRequestDTO nameRequestDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of buying an item.
         * @param String username
         * @param String name
         * @return none
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        Item itemBuy = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound(ItemConst.ITEM_NOT_FOUND);

        if (user.getGold() < itemBuy.getPrice()) throw new Conflict(ItemConst.YOU_DONT_HAVE_ENOUGH_GOLD);

        if (user.getInventory().getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY) throw new Conflict(ItemConst.INVENTORY_IS_FULL);

        user.getInventory().getItems().add(itemBuy);
        user.setGold(user.getGold() - itemBuy.getPrice());
        userRepository.save(user);

    }

    public void sellItem(String username, NameRequestDTO nameRequestDTO) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of selling an item.
         * @param String username
         * @param SellItemDTO sellItemDTO
         * @return none
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        Item itemBuy = itemRepository.findByName(nameRequestDTO.getName().toLowerCase());
        if (Objects.isNull(itemBuy)) throw new NotFound(ItemConst.ITEM_NOT_FOUND);

        if (!user.getInventory().getItems().contains(itemBuy))
            throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_INVENTORY);

        user.setGold(user.getGold() + (itemBuy.getPrice() / 2));
        user.getInventory().getItems().remove(itemBuy);
        userRepository.save(user);
    }

    public User equipItem(String username, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound(ItemConst.ITEM_NOT_FOUND));
        if (!user.getInventory().getItems().contains(item)) throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_INVENTORY);
        if (!Objects.equals(user.getAClass().getName(), item.getClassRequired()) && !Objects.equals(item.getClassRequired(), "none"))
            throw new Conflict(ItemConst.ITEM_DOES_NOT_CORRESPOND_TO_YOUR_CLASS);

        for (Item itemEquipedOld : user.getEquipment().getItems()) {
            if (!ItemConst.ITEM_ENABLED_TO_EQUIP.contains(itemEquipedOld.getType()))
                throw new Conflict(ItemConst.YOU_CANT_EQUIP_MORE_THAN_ONE + itemEquipedOld.getType() + " item");
            if (Objects.equals(itemEquipedOld.getType(), item.getType()))
                throw new Conflict(ItemConst.YOU_CANT_EQUIP_TWO_ITEMS_OF_THE_SAME_TYPE);
        }

        if (user.getLevel() < item.getLvlMin())
            throw new Conflict(ItemConst.YOU_CANT_EQUIP_AN_ITEM_THAT_REQUIRES_LEVEL + item.getLvlMin());

        if (Objects.equals(item.getType(), ItemConst.POTION_NAME)) {
            user.setHp(user.getMaxHp());
        } else {
            user.getEquipment().getItems().add(item);
            user.swapItemToEquipmentOrInventory(item, true);
        }
        user.getInventory().getItems().remove(item);

        userRepository.save(user);
        return user;
    }

    public User unequipItem(String username, EquipUnequipItemDTO equipUnequipItemDTO) throws Conflict {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of equipping or unequipping an item to the user.
         * @param String username
         * @param EquipUnequipItemDTO equipUnequipItemDTO
         * @return User
         */
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFound(ItemConst.USER_NOT_FOUND);

        Item item = itemRepository.findById(equipUnequipItemDTO.getId()).orElseThrow(() -> new NotFound(ItemConst.ITEM_NOT_FOUND));
        if (!user.getEquipment().getItems().contains(item)) throw new NotFound(ItemConst.ITEM_NOT_FOUND_IN_EQUIPMENT);
        if (user.getInventory().getItems().size() >= SvConfig.MAX_ITEMS_INVENTORY)
            throw new Conflict(ItemConst.INVENTORY_IS_FULL);

        user.getEquipment().getItems().remove(item);
        user.getInventory().getItems().add(item);

        user.swapItemToEquipmentOrInventory(item, false);
        if (user.getHp() > user.getMaxHp()) user.setHp(user.getMaxHp());
        userRepository.save(user);
        return user;
    }
}
