package com.gianca1994.aowebbackend.configurations;


import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @Author: Gianca1994
 * Explanation: ItemConfiguration
 */
@Configuration
public class ItemConfiguration {
    @Bean
    public CommandLineRunner autoSaveItems(ItemRepository itemRepository) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to auto save the items in the database.
         * @param ItemRepository itemRepository
         * @return CommandLineRunner
         */
        return args -> {
            List<Item> items = itemRepository.findAll();
            if (items.isEmpty()) {
                itemRepository.save(new Item("testSword", "weapon", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testShield", "shield", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testHelmet", "helmet", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testArmor", "armor", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testPants", "pants", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testGloves", "gloves", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testBoots", "boots", 1, "", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testShip", "ship", 1, "mage", 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("testWings", "wings", 1, "", 100, 0, 0, 0, 0, 0));
            }
        };
    }
}
