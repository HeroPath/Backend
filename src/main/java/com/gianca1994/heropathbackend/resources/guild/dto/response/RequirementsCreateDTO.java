package com.gianca1994.heropathbackend.resources.guild.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to create a new guild
 */

@AllArgsConstructor
@Getter
@Setter
public class RequirementsCreateDTO {
    private int lvl;
    private long gold;
    private int diamonds;
}
