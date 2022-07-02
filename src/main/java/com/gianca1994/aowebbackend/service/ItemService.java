package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.dto.ItemDTO;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(ItemDTO newItem) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        if (Objects.equals(newItem.getName(), "")) throw new IllegalArgumentException("Name cannot be empty");
        if (Objects.equals(newItem.getType(), "")) throw new IllegalArgumentException("Type cannot be empty");
        if (newItem.getLvlMin() <= 0) throw new IllegalArgumentException("LvlMin cannot be less than 0");
        if (newItem.getPrice() < 0) throw new IllegalArgumentException("Price cannot be less than 0");
        if (newItem.getStrength() < 0) throw new IllegalArgumentException("Strength cannot be less than 0");
        if (newItem.getDexterity() < 0) throw new IllegalArgumentException("Dexterity cannot be less than 0");
        if (newItem.getIntelligence() < 0) throw new IllegalArgumentException("Intelligence cannot be less than 0");
        if (newItem.getVitality() < 0) throw new IllegalArgumentException("Vitality cannot be less than 0");
        if (newItem.getLuck() < 0) throw new IllegalArgumentException("Luck cannot be less than 0");

        List<String> itemsEnabledToEquip = Arrays.asList("weapon", "shield", "helmet", "armor", "pants", "gloves", "boots", "ship", "wings");
        if (!itemsEnabledToEquip.contains(newItem.getType()))
            throw new ConflictException("You can't equip more than one " + newItem.getType() + " item");


        Item item = new Item();
        item.setName(newItem.getName());
        item.setType(newItem.getType());
        item.setLvlMin(newItem.getLvlMin());
        item.setClassRequired(newItem.getClassRequired());
        item.setPrice(newItem.getPrice());
        item.setStrength(newItem.getStrength());
        item.setDexterity(newItem.getDexterity());
        item.setIntelligence(newItem.getIntelligence());
        item.setVitality(newItem.getVitality());
        item.setLuck(newItem.getLuck());
        return itemRepository.save(item);
    }
}
