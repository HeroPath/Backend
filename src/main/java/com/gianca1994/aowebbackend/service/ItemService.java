package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.ItemDTO;
import com.gianca1994.aowebbackend.exception.BadRequest;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getClassShop(String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        List<Item> items = itemRepository.findAll();
        items.removeIf(item -> !item.getClassRequired().equals(aClass));
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
        if (item != null) throw new Conflict("Item already exists");

        if (Objects.equals(newItem.getName(), "")) throw new BadRequest("Name cannot be empty");
        if (Objects.equals(newItem.getType(), "")) throw new BadRequest("Type cannot be empty");
        if (newItem.getLvlMin() <= 0) throw new BadRequest("LvlMin cannot be less than 0");
        if (newItem.getPrice() < 0) throw new BadRequest("Price cannot be less than 0");

        if (newItem.getStrength() < 0 || newItem.getDexterity() < 0 || newItem.getIntelligence() < 0 ||
                newItem.getVitality() < 0 || newItem.getLuck() < 0
        ) throw new BadRequest("Stats cannot be less than 0");


        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        if (!itemsEnabledToEquip.contains(newItem.getType()))
            throw new Conflict("You can't equip more than one " + newItem.getType() + " item");

        if (Objects.equals(newItem.getClassRequired(), "")) newItem.setClassRequired("none");

        return itemRepository.save(new Item(
                newItem.getName().toLowerCase(),
                newItem.getType(),
                newItem.getLvlMin(),
                newItem.getClassRequired(),
                newItem.getPrice(),
                1,
                newItem.getStrength(),
                newItem.getDexterity(),
                newItem.getIntelligence(),
                newItem.getVitality(),
                newItem.getLuck())
        );
    }
}
