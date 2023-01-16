package com.gianca1994.aowebbackend.resources.guild;

import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to add users to a guild.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGuildDTO {
    private String username;
    private short level;
    private int titlePoints;
    private String className;
    private String titleName;
}
