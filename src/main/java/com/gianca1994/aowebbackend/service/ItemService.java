package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.ItemDTO;
import com.gianca1994.aowebbackend.exception.BadRequestException;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getClassShop(String aClass){
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

    public Item saveItem(ItemDTO newItem) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        Item item = itemRepository.findByName(newItem.getName().toLowerCase());
        if (item != null) throw new ConflictException("Item already exists");

        if (Objects.equals(newItem.getName(), "")) throw new BadRequestException("Name cannot be empty");
        if (Objects.equals(newItem.getType(), "")) throw new BadRequestException("Type cannot be empty");
        if (newItem.getLvlMin() <= 0) throw new BadRequestException("LvlMin cannot be less than 0");
        if (newItem.getPrice() < 0) throw new BadRequestException("Price cannot be less than 0");
        if (newItem.getStrength() < 0) throw new BadRequestException("Strength cannot be less than 0");
        if (newItem.getDexterity() < 0) throw new BadRequestException("Dexterity cannot be less than 0");
        if (newItem.getIntelligence() < 0) throw new BadRequestException("Intelligence cannot be less than 0");
        if (newItem.getVitality() < 0) throw new BadRequestException("Vitality cannot be less than 0");
        if (newItem.getLuck() < 0) throw new BadRequestException("Luck cannot be less than 0");

        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        if (!itemsEnabledToEquip.contains(newItem.getType()))
            throw new ConflictException("You can't equip more than one " + newItem.getType() + " item");

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
