package com.gianca1994.aowebbackend.resources.guild.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to send the ranking of the guilds
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpgradeDonateDTO {
    private int level;
    private int diamonds;
}
