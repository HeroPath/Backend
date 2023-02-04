package com.gianca1994.aowebbackend.resources.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: This class is the Role entity, it is used to store the roles of the users
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String roleName;
}
