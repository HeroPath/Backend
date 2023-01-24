package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.role.Role;
import com.gianca1994.aowebbackend.resources.title.Title;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

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
        user.setAClass(new Class());
        user.setTitle(new Title());
        user.setQuests(new HashSet<>());
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
                new Class(),
                new Title(),
                new Inventory(),
                new Equipment(),
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
    void givenUser_whenGetQuests_thenReturnQuests() {
        assertNotNull(user.getQuests());
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
    void givenUser_whenUpdateStatsMage_thenReturnMageStatsUpdate() {
        user.setAClass(new Class(1L, ModifConfig.MAGE_NAME, ModifConfig.MAGE_START_STR, ModifConfig.MAGE_START_DEX, ModifConfig.MAGE_START_INT, ModifConfig.MAGE_START_VIT, ModifConfig.MAGE_START_LUK));

        user.setStrength(ModifConfig.MAGE_START_STR);
        user.setDexterity(ModifConfig.MAGE_START_DEX);
        user.setIntelligence(ModifConfig.MAGE_START_INT);
        user.setVitality(ModifConfig.MAGE_START_VIT);
        user.setLuck(ModifConfig.MAGE_START_LUK);

        user.calculateStats(false);

        assertEquals(user.getMinDmg(), ModifConfig.MAGE_START_INT * ModifConfig.MIN_DMG_MAGE);
        assertEquals(user.getMaxDmg(), ModifConfig.MAGE_START_INT * ModifConfig.MAX_DMG_MAGE);
        assertEquals(user.getMaxHp(), ModifConfig.MAGE_START_VIT * ModifConfig.MAX_HP_MAGE);
        assertEquals(user.getDefense(), ModifConfig.MAGE_START_STR * ModifConfig.DEFENSE_MAGE);
        assertEquals(user.getEvasion(), ModifConfig.MAGE_START_DEX * ModifConfig.EVASION_MAGE);
        assertEquals(user.getCriticalChance(), ModifConfig.MAGE_START_LUK * ModifConfig.CRITICAL_MAGE > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : ModifConfig.MAGE_START_LUK * ModifConfig.CRITICAL_MAGE);

        user.calculateStats(true);

        assertEquals(user.getHp(), ModifConfig.MAGE_START_VIT * ModifConfig.MAX_HP_MAGE);
    }

    @Test
    void givenUser_whenUpdateStatsWarrior_thenReturnWarriorStatsUpdate() {
        user.setAClass(new Class(1L, ModifConfig.WARRIOR_NAME, ModifConfig.WARRIOR_START_STR, ModifConfig.WARRIOR_START_DEX, ModifConfig.WARRIOR_START_INT, ModifConfig.WARRIOR_START_VIT, ModifConfig.WARRIOR_START_LUK));

        user.setStrength(ModifConfig.WARRIOR_START_STR);
        user.setDexterity(ModifConfig.WARRIOR_START_DEX);
        user.setIntelligence(ModifConfig.WARRIOR_START_INT);
        user.setVitality(ModifConfig.WARRIOR_START_VIT);
        user.setLuck(ModifConfig.WARRIOR_START_LUK);

        user.calculateStats(false);

        assertEquals(user.getMinDmg(), ModifConfig.WARRIOR_START_STR * ModifConfig.MIN_DMG_WARRIOR);
        assertEquals(user.getMaxDmg(), ModifConfig.WARRIOR_START_STR * ModifConfig.MAX_DMG_WARRIOR);
        assertEquals(user.getMaxHp(), ModifConfig.WARRIOR_START_VIT * ModifConfig.MAX_HP_WARRIOR);
        assertEquals(user.getDefense(), ModifConfig.WARRIOR_START_INT * ModifConfig.DEFENSE_WARRIOR);
        assertEquals(user.getEvasion(), ModifConfig.WARRIOR_START_DEX * ModifConfig.EVASION_WARRIOR);
        assertEquals(user.getCriticalChance(), ModifConfig.WARRIOR_START_LUK * ModifConfig.CRITICAL_WARRIOR > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : ModifConfig.WARRIOR_START_LUK * ModifConfig.CRITICAL_WARRIOR);

        user.calculateStats(true);

        assertEquals(user.getHp(), ModifConfig.WARRIOR_START_VIT * ModifConfig.MAX_HP_WARRIOR);
    }

    @Test
    void givenUser_whenUpdateStatsArcher_thenReturnArcherStatsUpdate() {
        user.setAClass(new Class(1L, ModifConfig.ARCHER_NAME, ModifConfig.ARCHER_START_STR, ModifConfig.ARCHER_START_DEX, ModifConfig.ARCHER_START_INT, ModifConfig.ARCHER_START_VIT, ModifConfig.ARCHER_START_LUK));

        user.setStrength(ModifConfig.ARCHER_START_STR);
        user.setDexterity(ModifConfig.ARCHER_START_DEX);
        user.setIntelligence(ModifConfig.ARCHER_START_INT);
        user.setVitality(ModifConfig.ARCHER_START_VIT);
        user.setLuck(ModifConfig.ARCHER_START_LUK);

        user.calculateStats(false);

        assertEquals(user.getMinDmg(), ModifConfig.ARCHER_START_DEX * ModifConfig.MIN_DMG_ARCHER);
        assertEquals(user.getMaxDmg(), ModifConfig.ARCHER_START_DEX * ModifConfig.MAX_DMG_ARCHER);
        assertEquals(user.getMaxHp(), ModifConfig.ARCHER_START_VIT * ModifConfig.MAX_HP_ARCHER);
        assertEquals(user.getDefense(), ModifConfig.ARCHER_START_STR * ModifConfig.DEFENSE_ARCHER);
        assertEquals(user.getEvasion(), ModifConfig.ARCHER_START_INT * ModifConfig.EVASION_ARCHER);
        assertEquals(user.getCriticalChance(), ModifConfig.ARCHER_START_LUK * ModifConfig.CRITICAL_ARCHER > ModifConfig.MAX_CRITICAL_PERCENTAGE ? ModifConfig.MAX_CRITICAL_PERCENTAGE : ModifConfig.ARCHER_START_LUK * ModifConfig.CRITICAL_ARCHER);

        user.calculateStats(true);

        assertEquals(user.getHp(), ModifConfig.ARCHER_START_VIT * ModifConfig.MAX_HP_ARCHER);
    }
}