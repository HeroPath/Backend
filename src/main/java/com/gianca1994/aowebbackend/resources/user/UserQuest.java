package com.gianca1994.aowebbackend.resources.user;

import com.gianca1994.aowebbackend.resources.quest.Quest;

import javax.persistence.*;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to store the information about the user's quests.
 */

@Entity
@Table(name = "users_quests")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Integer getAmountNpcKill() {
        return amountNpcKill;
    }

    public void setAmountNpcKill(Integer amountNpcKill) {
        this.amountNpcKill = amountNpcKill;
    }

    public Integer getAmountUserKill() {
        return amountUserKill;
    }

    public void setAmountUserKill(Integer amountUserKill) {
        this.amountUserKill = amountUserKill;
    }
}

