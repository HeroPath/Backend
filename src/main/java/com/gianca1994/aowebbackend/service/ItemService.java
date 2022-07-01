package com.gianca1994.aowebbackend.service;

import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item saveItem(Item item) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        return itemRepository.save(item);
    }
}
