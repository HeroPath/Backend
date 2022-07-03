package com.gianca1994.aowebbackend.controller;


import com.gianca1994.aowebbackend.dto.ItemDTO;
import com.gianca1994.aowebbackend.exception.ConflictException;
import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/shop/{aClass}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STANDARD')")
    public List<Item> getMageShop(@PathVariable String aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of getting the items of a specific class.
         * @param String aClass
         * @return List<Item>
         */
        return itemService.getMageShop(aClass);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public Item saveItem(@RequestBody ItemDTO newItem) throws ConflictException {
        /**
         * @Author: Gianca1994
         * Explanation: This function is in charge of saving an item.
         * @param Item item
         * @return Item
         */
        return itemService.saveItem(newItem);
    }
}
