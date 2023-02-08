package com.gianca1994.aowebbackend.resources.guild.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuildUpgradeDonateDTO {
    private int level;
    private int diamonds;
}
