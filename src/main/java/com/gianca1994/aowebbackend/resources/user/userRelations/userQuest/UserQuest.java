package com.gianca1994.aowebbackend.resources.user.userRelations.userQuest;

import com.gianca1994.aowebbackend.resources.quest.Quest;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @Author: Gianca1994
 * Explanation: This class is the entity of the user_quest table
 */

@Entity
@Table(name = "users_quests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @Column(name = "amount_npc_kill")
    private Integer amountNpcKill;

    @Column(name = "amount_user_kill")
    private Integer amountUserKill;
}
