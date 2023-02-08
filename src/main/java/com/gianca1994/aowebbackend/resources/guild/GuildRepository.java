package com.gianca1994.aowebbackend.resources.guild;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This is the repository for the Guild class.
 */

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
    boolean existsGuildByName(String name);
    boolean existsGuildByTag(String tag);

    Guild findByName(String name);
    List<Guild> findAllByOrderByTitlePointsDesc();

    @Query("SELECT CASE WHEN g.leader = :username OR g.subLeader = :username THEN true ELSE false END FROM Guild g WHERE g.name = :guildName")
    boolean isLeaderOrSubLeader(String username, String guildName);

    @Query("SELECT g.level FROM Guild g WHERE g.name = :name")
    int findLevelByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Guild g SET g.level = :level WHERE g.name = :name")
    void updateLevelByName(@Param("level") int level, @Param("name") String name);

    @Query("SELECT g.diamonds FROM Guild g WHERE g.name = :name")
    int findDiamondsByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Guild g SET g.diamonds = :diamonds WHERE g.name = :name")
    void updateDiamondsByName(@Param("diamonds") int diamonds, @Param("name") String name);

}

