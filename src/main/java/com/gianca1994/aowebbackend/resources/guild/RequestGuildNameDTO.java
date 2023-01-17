package com.gianca1994.aowebbackend.resources.guild;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to request a guild name.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestGuildNameDTO {
    private String name;
}
