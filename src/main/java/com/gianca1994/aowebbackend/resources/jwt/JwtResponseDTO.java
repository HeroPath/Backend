package com.gianca1994.aowebbackend.resources.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author: Gianca1994
 * Explanation: JwtRequestDTO
 */

@Getter
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
}
