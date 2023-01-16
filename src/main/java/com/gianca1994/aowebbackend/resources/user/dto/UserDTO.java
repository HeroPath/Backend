package com.gianca1994.aowebbackend.resources.user.dto;

import lombok.*;

/**
 * @Author: Gianca1994
 * Explanation: UserDTO
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private long classId;
}
