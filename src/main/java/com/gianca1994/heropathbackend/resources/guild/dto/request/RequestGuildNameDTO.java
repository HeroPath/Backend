package com.gianca1994.heropathbackend.resources.guild.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for the guild resource
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestGuildNameDTO {
    private String name;
}
