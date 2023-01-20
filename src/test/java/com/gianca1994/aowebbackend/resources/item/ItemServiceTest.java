package com.gianca1994.aowebbackend.resources.item;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.classes.ClassRepository;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.guild.Guild;
import com.gianca1994.aowebbackend.resources.guild.GuildRepository;
import com.gianca1994.aowebbackend.resources.guild.GuildService;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();

        itemRepository.save(new Item(
                "testitem", "testtype", 1,
                "none", 1,
                1, 1, 1, 1, 1
        ));
    }

    @Test
    void getClassShop() {
        assertEquals(1, itemService.getClassShop("none").size());
    }

    @Test
    void getClassShopNotFound() {
        assertThrows(NotFound.class, () -> itemService.getClassShop("test"));
    }

    @Test
    void saveItem() {
        ItemDTO itemDTO = new ItemDTO(
                "testitem2", "armor", 1,
                "none", 1,
                1, 1, 1, 1, 1
        );

        try {
            itemService.saveItem(itemDTO);
        } catch (Conflict e) {
            throw new RuntimeException(e);
        }
        assertEquals(2, itemRepository.findAll().size());
        assertAll(
                () -> assertEquals("testitem2", itemRepository.findAll().get(1).getName()),
                () -> assertEquals("armor", itemRepository.findAll().get(1).getType()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getLvlMin()),
                () -> assertEquals("none", itemRepository.findAll().get(1).getClassRequired()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getPrice()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getStrength()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getDexterity()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getIntelligence()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getVitality()),
                () -> assertEquals(1, itemRepository.findAll().get(1).getLuck())
        );
    }


}