package com.gianca1994.heropathbackend.resources.guild.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for the request of donating diamonds to the guild
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuildDonateDiamondsDTO {
    private int amountDiamonds;
}
