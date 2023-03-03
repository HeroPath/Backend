package com.gianca1994.heropathbackend.resources.user.dto.request;

import lombok.*;

/**
 * @Author: Gianca1994
 * @Explanation: DTO for the register of the user
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    private String username;
    private String password;
    private String email;
    private String className;
}
