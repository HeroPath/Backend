package com.gianca1994.aowebbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
}
