package com.gianca1994.aowebbackend.resources.guild;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GuildServiceTest {

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
    private GuildRepository guildRepository;

    @Autowired
    private GuildService guildService;

    @BeforeEach
    void setUp() {
        guildRepository.deleteAll();

        User userTest;

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

        Guild guild = new Guild();
        guild.setName("testguild");
        guild.setDescription("testdescription");
        guild.setTag("testtag");
        guild.setLeader(userTest.getUsername());
        guild.setSubLeader("");
        guild.getMembers().add(userTest);
        guildRepository.save(guild);
    }

    @Test
    void getAllGuilds() {
        assertThat(guildService.getAllGuilds().size()).isEqualTo(1);
    }

    @Test
    void getGuildByName() {
        assertThat(guildService.getGuildByName("testguild").get("name").asText()).isEqualTo("testguild");
    }

}