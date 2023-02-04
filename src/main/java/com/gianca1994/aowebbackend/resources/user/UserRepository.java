package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.resources.user.dto.queyModel.UserAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @Author: Gianca1994
 * Explanation: UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Page List ordered by level, title, points and experience
    Page<User> findAllByOrderByLevelDescTitlePointsDescExperienceDesc(PageRequest of);

    // get user attributes
    @Query("SELECT new com.gianca1994.aowebbackend.resources.user.dto.queyModel.UserAttributes(u.aClass, u.strength, u.dexterity, u.vitality, u.intelligence, u.luck, u.freeSkillPoints, u.maxDmg, u.minDmg, u.maxHp, u.hp, u.defense, u.evasion, u.criticalChance) " +
            "FROM User u WHERE u.id = :id")
    UserAttributes findAttributesByUserId(@Param("id") Long id);

    // update user stats
    @Modifying
    @Query("UPDATE User u SET u.strength = :strength, u.dexterity = :dexterity, u.vitality = :vitality, u.intelligence = :intelligence, u.luck = :luck, u.freeSkillPoints = :freeSkillPoints, u.maxDmg = :maxDmg, u.minDmg = :minDmg, u.maxHp = :maxHp, u.hp = :hp, u.defense = :defense, u.evasion = :evasion, u.criticalChance = :criticalChance WHERE u.id = :id")
    void updateUserStats(@Param("strength") int strength, @Param("dexterity") int dexterity, @Param("vitality") int vitality, @Param("intelligence") int intelligence, @Param("luck") int luck, @Param("freeSkillPoints") int freeSkillPoints, @Param("maxDmg") int maxDmg, @Param("minDmg") int minDmg, @Param("maxHp") int maxHp, @Param("hp") int hp, @Param("defense") int defense, @Param("evasion") int evasion, @Param("criticalChance") float criticalChance, @Param("id") Long id);

}
