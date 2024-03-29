package com.gianca1994.heropathbackend.resources.user;

import com.gianca1994.heropathbackend.resources.inventory.Inventory;
import com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * @Author: Gianca1994
 * @Explanation: This interface is used to perform queries on the database.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    //////////////////// USED FOR MARKET_SERVICE ////////////////////
    @Query("SELECT u.gold FROM User u WHERE u.id = :userId")
    Long findGoldByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.gold = :gold WHERE u.id = :userId")
    void updateGoldByUserId(@Param("userId") Long userId, @Param("gold") Long gold);

    @Query("SELECT u.inventory FROM User u WHERE u.id = :id")
    Inventory findInventoryById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.inventory = :inventory WHERE u.id = :id")
    void updateInventoryById(@Param("id") long id, @Param("inventory") Inventory inventory);
    /////////////////////////////////////////////////////////////////

    //////////////////// USED FOR AUTH_SERVICE ////////////////////
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    ///////////////////////////////////////////////////////////////

    //////////////////// USED FOR GUILD_SERVICE ////////////////////
    @Query("SELECT u.guildName FROM User u WHERE u.id = :id")
    String findGuildNameByUserId(@Param("id") Long id);

    @Query("SELECT u.diamond FROM User u WHERE u.id = :id")
    int findDiamondByUserId(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.diamond = :diamond WHERE u.id = :id")
    void updateUserDiamond(@Param("id") Long id, @Param("diamond") int diamond);
    ////////////////////////////////////////////////////////////////

    //////////////////// USED FOR USER_SERVICE ////////////////////
    Page<User> findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest of);

    @Query("SELECT new com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO(u.username, u.level, u.titlePoints, u.aClass, u.titleName) "
            + "FROM User u WHERE u.id = :id")
    UserGuildDTO getUserForGuild(@Param("id") Long id);

    @Query("SELECT new com.gianca1994.heropathbackend.resources.user.dto.queyModel.UserAttributes(u.aClass, u.strength, u.dexterity, u.vitality, u.intelligence, u.luck, u.freeSkillPoints, u.maxDmg, u.minDmg, u.maxHp, u.hp, u.defense, u.evasion, u.criticalChance) " +
            "FROM User u WHERE u.id = :id")
    UserAttributes findAttributesByUserId(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.strength = :strength, u.dexterity = :dexterity, u.vitality = :vitality, u.intelligence = :intelligence, u.luck = :luck, u.freeSkillPoints = :freeSkillPoints, u.maxDmg = :maxDmg, u.minDmg = :minDmg, u.maxHp = :maxHp, u.hp = :hp, u.defense = :defense, u.evasion = :evasion, u.criticalChance = :criticalChance WHERE u.id = :id")
    void updateUserStats(@Param("strength") int strength, @Param("dexterity") int dexterity, @Param("vitality") int vitality, @Param("intelligence") int intelligence, @Param("luck") int luck, @Param("freeSkillPoints") int freeSkillPoints, @Param("maxDmg") int maxDmg, @Param("minDmg") int minDmg, @Param("maxHp") int maxHp, @Param("hp") int hp, @Param("defense") int defense, @Param("evasion") int evasion, @Param("criticalChance") float criticalChance, @Param("id") Long id);
    ////////////////////////////////////////////////////////////////
}
