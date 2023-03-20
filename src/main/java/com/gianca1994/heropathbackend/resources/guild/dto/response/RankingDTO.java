package com.gianca1994.heropathbackend.resources.guild.dto.response;


import com.gianca1994.heropathbackend.config.SvConfig;
import com.gianca1994.heropathbackend.resources.guild.Guild;
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
public class RankingDTO {
    private String name;
    private String tag;
    private String leader;
    private int memberAmount;
    private int maxMembers;
    private int titlePoints;
    private short level;

    public RankingDTO guildRankingDTO(Guild guild) {
        /**
         * @Author: Gianca1994
         * @Explanation: This method converts a Guild object into a GuildRankingDTO object
         * @param guild: Guild object
         * @return GuildRankingDTO
         */
        return new RankingDTO(
                guild.getName(),
                guild.getTag(),
                guild.getLeader(),
                guild.getMembers().size(),
                SvConfig.MAX_MEMBERS_IN_GUILD,
                guild.getTitlePoints(),
                guild.getLevel()
        );
    }
}
