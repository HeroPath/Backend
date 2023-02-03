package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.title.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test@test.com");
        user.setRole(new Role());
        user.setAClass("test");
        user.setTitle(new Title());
        user.setInventory(new Inventory());
        user.setEquipment(new Equipment());
        user.setLevel((short) 1);
        user.setExperience(0);
        user.setExperienceToNextLevel(100);
        user.setGold(0);
        user.setDiamond(0);
        user.setMaxDmg(0);
        user.setMinDmg(0);
        user.setMaxHp(0);
        user.setHp(0);
        user.setDefense(0);
        user.setEvasion(0);
        user.setCriticalChance(0);
        user.setStrength(0);
        user.setDexterity(0);
        user.setIntelligence(0);
        user.setVitality(0);
        user.setLuck(0);
        user.setFreeSkillPoints(0);
        user.setNpcKills(0);
        user.setPvpWins(0);
        user.setPvpLosses(0);
        user.setTitlePoints(0);
        user.setGuildName("test");
    }

    @Test
    void constructorNotArgsTest() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void constructorNotAllArgsTest() {
        User user = new User(
                "test",
                "test",
                "test@test.com",
                new Role(),
                new Title(),
                new Inventory(),
                new Equipment(),
                "test",
                0,
                0,
                0,
                0,
                0
        );
        assertNotNull(user);
    }

    @Test
    void givenUser_whenGetId_thenReturnId() {
        assertEquals(user.getId(), 1L);
    }

    @Test
    void givenUser_whenGetUsername_thenReturnUsername() {
        assertEquals(user.getUsername(), "test");
    }

    @Test
    void givenUser_whenGetPassword_thenReturnPassword() {
        assertEquals(user.getPassword(), "test");
    }

    @Test
    void givenUser_whenGetEmail_thenReturnEmail() {
        assertEquals(user.getEmail(), "test@test.com");
    }

    @Test
    void givenUser_whenGetRole_thenReturnRole() {
        assertNotNull(user.getRole());
    }

    @Test
    void givenUser_whenGetAClass_thenReturnAClass() {
        assertNotNull(user.getAClass());
    }

    @Test
    void givenUser_whenGetTitle_thenReturnTitle() {
        assertNotNull(user.getTitle());
    }

    @Test
    void givenUser_whenGetInventory_thenReturnInventory() {
        assertNotNull(user.getInventory());
    }

    @Test
    void givenUser_whenGetEquipment_thenReturnEquipment() {
        assertNotNull(user.getEquipment());
    }

    @Test
    void givenUser_whenGetLevel_thenReturnLevel() {
        assertEquals(user.getLevel(), (short) 1);
    }

    @Test
    void givenUser_whenGetExperience_thenReturnExperience() {
        assertEquals(user.getExperience(), 0);
    }

    @Test
    void givenUser_whenGetExperienceToNextLevel_thenReturnExperienceToNextLevel() {
        assertEquals(user.getExperienceToNextLevel(), 100);
    }

    @Test
    void givenUser_whenGetGold_thenReturnGold() {
        assertEquals(user.getGold(), 0);
    }

    @Test
    void givenUser_whenGetDiamond_thenReturnDiamond() {
        assertEquals(user.getDiamond(), 0);
    }

    @Test
    void givenUser_whenGetMaxDmg_thenReturnMaxDmg() {
        assertEquals(user.getMaxDmg(), 0);
    }

    @Test
    void givenUser_whenGetMinDmg_thenReturnMinDmg() {
        assertEquals(user.getMinDmg(), 0);
    }

    @Test
    void givenUser_whenGetMaxHp_thenReturnMaxHp() {
        assertEquals(user.getMaxHp(), 0);
    }

    @Test
    void givenUser_whenGetHp_thenReturnHp() {
        assertEquals(user.getHp(), 0);
    }

    @Test
    void givenUser_whenGetDefense_thenReturnDefense() {
        assertEquals(user.getDefense(), 0);
    }

    @Test
    void givenUser_whenGetEvasion_thenReturnEvasion() {
        assertEquals(user.getEvasion(), 0);
    }

    @Test
    void givenUser_whenGetCriticalChance_thenReturnCriticalChance() {
        assertEquals(user.getCriticalChance(), 0);
    }

    @Test
    void givenUser_whenGetStrength_thenReturnStrength() {
        assertEquals(user.getStrength(), 0);
    }

    @Test
    void givenUser_whenGetDexterity_thenReturnDexterity() {
        assertEquals(user.getDexterity(), 0);
    }

    @Test
    void givenUser_whenGetIntelligence_thenReturnIntelligence() {
        assertEquals(user.getIntelligence(), 0);
    }

    @Test
    void givenUser_whenGetVitality_thenReturnVitality() {
        assertEquals(user.getVitality(), 0);
    }

    @Test
    void givenUser_whenGetLuck_thenReturnLuck() {
        assertEquals(user.getLuck(), 0);
    }

    @Test
    void givenUser_whenGetFreeSkillPoints_thenReturnFreeSkillPoints() {
        assertEquals(user.getFreeSkillPoints(), 0);
    }

    @Test
    void givenUser_whenGetNpcKills_thenReturnNpcKills() {
        assertEquals(user.getNpcKills(), 0);
    }

    @Test
    void givenUser_whenGetPvpWins_thenReturnPvpWins() {
        assertEquals(user.getPvpWins(), 0);
    }

    @Test
    void givenUser_whenGetPvpLoses_thenReturnPvpLoses() {
        assertEquals(user.getPvpLosses(), 0);
    }

    @Test
    void givenUser_whenGetUserTitlePoints_thenReturnUserTitlePoints() {
        assertEquals(user.getTitlePoints(), 0);
    }

    @Test
    void givenUser_whenGetGuildName_thenReturnGuildName() {
        assertEquals(user.getGuildName(), "test");
    }


    @Test
    void givenAmount_whenAddTitlePoints_thenReturnUserUpdated() {
        user.setTitlePoints(0);
        user.addTitlePoints(10);

        assertEquals(10, user.getTitlePoints());
    }

    @Test
    void givenAmount_whenRemoveTitlePoints_thenReturnUserUpdated() {
        user.setTitlePoints(10);
        user.removeTitlePoints(10);
        assertEquals(0, user.getTitlePoints());

        user.removeTitlePoints(50);
        assertEquals(0, user.getTitlePoints());
    }
}