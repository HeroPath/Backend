package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.classes.ClassRepository;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.jwt.JWTAuthController;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
import com.gianca1994.aowebbackend.resources.user.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuestServiceTest {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private JWTAuthController jwtAuthController;

    private User userTest;

    private final NameRequestDTO questRequestDTO = new NameRequestDTO("testquest");

    @BeforeEach
    void setUp() throws Conflict {
        questRepository.deleteAll();

        if (userRepository.findAll().size() >= 1) {
            userTest = userRepository.findAll().get(0);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername("testusername");
            userDTO.setPassword("test");
            userDTO.setEmail("test@test.com");
            userDTO.setClassId(1);

            jwtAuthController.saveUser(userDTO);
            userTest = userRepository.findByUsername("testusername");
        }

        questRepository.save(
                new Quest(
                        "testquest",
                        "testquestdescription",
                        "testnpc",
                        1,
                        1,
                        1,
                        1,
                        (short) 1
                )
        );
    }


    @Test
    @Order(2)
    void givenNameQuest_whenGetQuestByName_thenReturnQuest() {
        assertEquals("testquest", questService.getQuestByName("testquest").getName());
    }


    @Test
    @Order(5)
    void givenUsernameAndNameRequestDTO_whenAcceptQuest_thenReturnAddQuestInUser() throws Conflict {
        assertEquals(0, userTest.getUserQuests().size());

        questService.acceptQuest(userTest.getUsername(), questRequestDTO);
        User userBefore = userRepository.findByUsername(userTest.getUsername());

        assertEquals(1, userBefore.getUserQuests().size());
        assertEquals("testquest", Objects.requireNonNull(userBefore.getUserQuests().stream().findFirst().orElse(null)).getQuest().getName());
    }
}