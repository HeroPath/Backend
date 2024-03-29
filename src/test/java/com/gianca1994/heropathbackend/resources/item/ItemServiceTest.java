package com.gianca1994.heropathbackend.resources.item;

import com.gianca1994.heropathbackend.resources.item.dto.request.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
        itemRepository.save(new Item(
                "testitem", "testtype", 1,
                1, "none",
                1, 1, 1, 1, 1, true
        ));
    }

    @Test
    void givenClassNames_whenGetClassShop_thenReturnItems() {
        assertEquals(1, itemService.getClassShop("none").size());
    }

    @Test
    void givenItemDTO_whenSaveItem_thenReturnItem() {
        ItemDTO itemDTO = new ItemDTO(
                "testitem2", "armor", 1,
                "none", 1,
                1, 1, 1, 1, 1, true
        );


        itemService.saveItem(itemDTO);

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