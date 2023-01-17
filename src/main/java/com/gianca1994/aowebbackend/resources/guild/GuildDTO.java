package com.gianca1994.aowebbackend.resources.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: GuildDTO
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuildDTO {
    private String name;
    private String description;
    private String tag;
}
