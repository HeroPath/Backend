package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Gianca1994
 * Explanation: This is the Guild class.
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

    public Guild(String name, String description, String tag, String leader, short level, int diamonds) {
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.leader = leader;
        this.subLeader = "";
        this.level = level;
        this.diamonds = diamonds;
        this.titlePoints = 0;
    }
}