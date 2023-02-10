package com.gianca1994.aowebbackend.resources.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: DTO for the user ranking
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRankingDTO {
    private int rankPosition;
    private String username;
    private String guildName;
    private String aclassName;
    private int level;
    private String titleName;
    private int titlePoints;
    private int strength;
    private int dexterity;
    private int vitality;
    private int intelligence;
    private int luck;
    private int pvpWins;
    private int pvpLosses;
}
