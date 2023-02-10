package com.gianca1994.aowebbackend.resources.user.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to return the user's guild information
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGuildDTO {
    private String username;
    private short level;
    private int titlePoints;
    private String aClass;
    private String titleName;
}
