package com.gianca1994.heropathbackend.resources.guild.dto.response;

import com.gianca1994.heropathbackend.resources.guild.Guild;
import com.gianca1994.heropathbackend.resources.user.UserService;
import com.gianca1994.heropathbackend.resources.user.dto.response.UserGuildDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to send the guild information to the user.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private boolean userInGuild;
    private String username;
    private String name;
    private String tag;
    private String description;
    private String leader;
    private String subLeader;
    private int memberAmount;
    private int level;
    private int diamonds;
    private int titlePoints;
    private int maxMembers;
    private List<UserGuildDTO> members;
    private Set<UserGuildDTO> requests;

    public UserDTO(boolean userInGuild) {
        this.userInGuild = userInGuild;
    }

    public void updateDTO(String username, String name, String tag, String description, String leader, String subLeader, int memberAmount, int level, int diamonds, int titlePoints, int maxMembers) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method updates the guild DTO.
         * @param String username, String name, String tag, String description, String leader, String subLeader, int memberAmount, int level, int diamonds, int titlePoints, int maxMembers
         * @return void
         */
        this.username = username;
        this.name = name;
        this.tag = tag;
        this.description = description;
        this.leader = leader;
        this.subLeader = subLeader;
        this.memberAmount = memberAmount;
        this.level = level;
        this.diamonds = diamonds;
        this.titlePoints = titlePoints;
        this.maxMembers = maxMembers;
    }

    public void createMembersList(Guild guild, UserService userService) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method creates a list of users sorted by title points and level.
         * @param Guild guild
         * @param UserService userService
         * @return void
         */
        this.members = guild.getMembers().stream()
                .map(user -> userService.getUserForGuild(user.getId()))
                .sorted((user1, user2) -> {
                    if (user1.getUsername().equals(guild.getLeader())) return -1;
                    else if (user2.getUsername().equals(guild.getLeader())) return 1;
                    else if (user1.getUsername().equals(guild.getSubLeader())) return -1;
                    else if (user2.getUsername().equals(guild.getSubLeader())) return 1;
                    else if (user1.getTitlePoints() == user2.getTitlePoints())
                        return -1 * Integer.compare(user1.getLevel(), user2.getLevel());
                    else return -1 * Integer.compare(user1.getTitlePoints(), user2.getTitlePoints());
                }).collect(Collectors.toList());
    }

    public void createRequestList(Guild guild, UserService userService){
        /**
         * @Author: Gianca1994
         * @Explanation: This method creates a list of users that have requested to join the guild.
         * @param Guild guild
         * @param UserService userService
         * @return void
         */
        this.requests = guild.getRequests().stream()
                .map(user -> userService.getUserForGuild(user.getId()))
                .collect(Collectors.toSet());
    }
}
