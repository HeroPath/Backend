package com.gianca1994.aowebbackend.resources.quest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.resources.user.userRelations.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


/**
 * @Author: Gianca1994
 * Explanation: Class
 */

@Entity
@Table(name = "quests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private String nameNpcKill;

    @Column
    private int npcKillAmountNeeded;

    @Column
    private int userKillAmountNeeded;

    @Column
    private long giveExp;

    @Column
    private long giveGold;

    @Column
    private short giveDiamonds;

    @OneToMany(mappedBy = "quest", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserQuest> userQuests;

    public Quest(String name, String description, String nameNpcKill, int npcKillAmountNeeded, int userKillAmountNeeded, long giveExp, long giveGold, short giveDiamonds) {
        this.name = name;
        this.description = description;
        this.nameNpcKill = nameNpcKill;
        this.npcKillAmountNeeded = npcKillAmountNeeded;
        this.userKillAmountNeeded = userKillAmountNeeded;
        this.giveExp = giveExp;
        this.giveGold = giveGold;
        this.giveDiamonds = giveDiamonds;
    }
}
