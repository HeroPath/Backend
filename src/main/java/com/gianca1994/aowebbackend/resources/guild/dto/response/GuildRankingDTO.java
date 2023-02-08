package com.gianca1994.aowebbackend.resources.guild.dto.response;


import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.guild.Guild;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuildRankingDTO {
    private String name;
    private String description;
    private String tag;
    private String leader;
    private String subLeader;
    private int memberAmount;
    private int maxMembers;
    private int titlePoints;
    private short level;

    public GuildRankingDTO guildRankingDTO(Guild guild) {
        /**
         * @Author: Gianca1994
         * Explanation: This method converts a Guild object into a GuildRankingDTO object
         * @param guild: Guild object
         * @return GuildRankingDTO
         */
        return new GuildRankingDTO(
                guild.getName(),
                guild.getDescription(),
                guild.getTag(),
                guild.getLeader(),
                guild.getSubLeader(),
                guild.getMembers().size(),
                SvConfig.MAX_MEMBERS_IN_GUILD,
                guild.getTitlePoints(),
                guild.getLevel()
        );
    }
}
