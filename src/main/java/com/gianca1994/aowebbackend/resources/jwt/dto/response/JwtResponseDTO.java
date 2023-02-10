package com.gianca1994.aowebbackend.resources.jwt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author: Gianca1994
 * Explanation: This class is used to return the JWT token to the user
 */

@Getter
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
}
