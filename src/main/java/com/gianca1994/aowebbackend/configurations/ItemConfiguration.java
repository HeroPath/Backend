package com.gianca1994.aowebbackend.configurations;


import com.gianca1994.aowebbackend.model.Item;
import com.gianca1994.aowebbackend.model.Role;
import com.gianca1994.aowebbackend.repository.ItemRepository;
import com.gianca1994.aowebbackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ItemConfiguration {
    @Bean
    public CommandLineRunner autoSaveItems(ItemRepository itemRepository) {
        return args -> {
            List<Item> items = itemRepository.findAll();
            if (items.isEmpty()) {
                itemRepository.save(new Item("Espada de hielo", "weapon", (short) 1, 100, 350, 10, 0, 50, 0));
                itemRepository.save(new Item("Escudo de hielo", "shield", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Casco de hielo", "helmet", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Armadura de hielo", "armor", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Pantalones de hielo", "pants", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Guantes de hielo", "gloves", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Botas de hielo", "boots", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Barca de hielo", "ship", (short) 1, 100, 0, 0, 0, 0, 0));
                itemRepository.save(new Item("Alas de hielo", "wings", (short) 1, "mage", 100, 1, 0, 0, 0, 0, 0));
            }
        };
    }
}
