package com.gianca1994.heropathbackend.resources.quest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.heropathbackend.resources.user.userRelations.userQuest.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


/**
 * @Author: Gianca1994
 * @Explanation: This class is the Quest entity, it is used to create the Quest table in the database.
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
    private String nameNpcKill;

    @Column
    private int npcAmountNeed;

    @Column
    private int userAmountNeed;

    @Column
    private int giveExp;

    @Column
    private long giveGold;

    @Column
    private short giveDiamonds;

    @OneToMany(mappedBy = "quest", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserQuest> userQuests;

    public Quest(String name, String nameNpcKill, int npcAmountNeed, int userAmountNeed, int giveExp, long giveGold, short giveDiamonds) {
        this.name = name;
        this.nameNpcKill = nameNpcKill;
        this.npcAmountNeed = npcAmountNeed;
        this.userAmountNeed = userAmountNeed;
        this.giveExp = giveExp;
        this.giveGold = giveGold;
        this.giveDiamonds = giveDiamonds;
    }
}
