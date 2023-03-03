package com.gianca1994.heropathbackend.resources.user.userRelations.userQuest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Gianca1994
 * @Explanation: This is the repository for the UserQuest entity.
 */

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    List<UserQuest> findByUserUsername(String username);
    UserQuest findByUserUsernameAndQuestName(String username, String questName);
}
