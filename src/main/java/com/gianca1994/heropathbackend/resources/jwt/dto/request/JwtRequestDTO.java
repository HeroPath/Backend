package com.gianca1994.heropathbackend.resources.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author: Gianca1994
 * @Explanation: This class is used to receive the username and password from the client
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
}
