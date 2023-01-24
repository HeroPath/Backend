package com.gianca1994.aowebbackend.resources.quest;

import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.exception.Conflict;
import com.gianca1994.aowebbackend.exception.NotFound;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.classes.ClassRepository;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.equipment.EquipmentRepository;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.inventory.InventoryRepository;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.role.RoleRepository;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.title.TitleRepository;
import com.gianca1994.aowebbackend.resources.user.User;
import com.gianca1994.aowebbackend.resources.user.UserRepository;
import com.gianca1994.aowebbackend.resources.user.dto.NameRequestDTO;
import lombok.SneakyThrows;
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
    private QuestRepository questRepository;

    @Autowired
    private QuestService questService;

    private User userTest;

    private final NameRequestDTO questRequestDTO = new NameRequestDTO("testquest");

    @BeforeEach
    void setUp() {
        questRepository.deleteAll();

        if (userRepository.findAll().size() >= 1) {
            userTest = userRepository.findAll().get(0);
        } else {
            Class testClass = classRepository.findById(1L).get();
            Title testTitle = titleRepository.findById(1L).get();
            Role testRole = roleRepository.findById(1L).get();
            Inventory inventory = new Inventory();
            Equipment equipment = new Equipment();

            inventoryRepository.save(inventory);
            equipmentRepository.save(equipment);

            userTest = new User(
                    "testusername", "testpassword", "testusername@test.com",
                    testRole, testClass, testTitle, inventory, equipment,
                    1, 1, 1, 1, 1
            );
            userRepository.save(userTest);
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
    @Order(1)
    void givenUsername_whenGetQuestsByUsername_thenReturnQuests() {
        assertEquals(1, questService.getAllQuests(userTest.getUsername()).size());
    }

    @Test
    @Order(2)
    void givenNameQuest_whenGetQuestByName_thenReturnQuest() {
        assertEquals("testquest", questService.getQuestByName("testquest").getName());
    }

    @Test
    @Order(3)
    void givenQuestDTO_whenCreateQuest_thenReturnQuest() {
        QuestDTO questDTO = new QuestDTO(
                "testquest2",
                "testquestdescription2",
                "testnpc2",
                2,
                2,
                2,
                2,
                (short) 2
        );
        questService.saveQuest(questDTO);

        assertEquals(2, questService.getAllQuests(userTest.getUsername()).size());
        assertEquals("testquest2", questService.getQuestByName("testquest2").getName());
    }

    @Test
    @Order(4)
    void givenNameQuest_whenDeleteQuest_thenReturnQuest() {
        questService.deleteQuest("testquest");
        assertEquals(0, questService.getAllQuests(userTest.getUsername()).size());
    }

    @Test
    @Order(5)
    void givenUsernameAndNameRequestDTO_whenAcceptQuest_thenReturnAddQuestInUser() throws Conflict {
        assertEquals(0, userTest.getQuests().size());

        questService.acceptQuest(userTest.getUsername(), questRequestDTO);
        User userBefore = userRepository.findByUsername(userTest.getUsername());

        assertEquals(1, userBefore.getQuests().size());
        assertEquals("testquest", Objects.requireNonNull(userBefore.getQuests().stream().findFirst().orElse(null)).getName());
    }
}