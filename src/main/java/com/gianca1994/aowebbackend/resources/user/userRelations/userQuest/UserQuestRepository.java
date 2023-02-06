package com.gianca1994.aowebbackend.resources.user.userRelations.userQuest;

import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to store the information about the user's quests.
 */

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    List<UserQuest> findByUserUsername(String username);
    UserQuest findByUserUsernameAndQuestName(String username, String questName);
}
