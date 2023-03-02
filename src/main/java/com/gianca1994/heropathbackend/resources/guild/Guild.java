package com.gianca1994.heropathbackend.resources.guild;

import com.gianca1994.heropathbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

/**
 * @Author: Gianca1994
 * @Explanation: This class represents the guild entity
 */

@Entity
@Table(name = "guilds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(unique = true, nullable = false)
    private String tag;

    @Column
    private String leader;

    @Column
    private String subLeader;

    @Column
    private short level;

    @Column
    private int diamonds;

    @Column
    private int titlePoints;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guilds_users",
            joinColumns = @JoinColumn(name = "guild_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> members = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "guilds_invites",
            joinColumns = @JoinColumn(name = "guild_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> requests = new HashSet<>();

    public Guild(String name, String description, String tag, String leader, int titlePoints, User userLeader) {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.leader = leader;
        this.subLeader = "";
        this.level = 1;
        this.diamonds = 0;
        this.titlePoints = titlePoints;
        this.members.add(userLeader);
    }

    public void userAddGuild(User user) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method adds a user to the guild
         * @param user - User to be added
         * @return void
         */
        this.getRequests().remove(user);
        this.getMembers().add(user);
        this.titlePoints += user.getTitlePoints();
    }

    public void userRemoveGuild(User user) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method removes a user from the guild
         * @param user - User to be removed
         * @return void
         */
        if (user.getUsername().equals(this.getSubLeader())) this.setSubLeader("");
        if (Objects.equals(user.getUsername(), this.getLeader())) {
            this.setLeader(this.getSubLeader());
            this.setSubLeader("");
        }
        this.getMembers().remove(user);
        this.setTitlePoints(this.getTitlePoints() - user.getTitlePoints());
    }
}