package com.gianca1994.aowebbackend.resources.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Gianca1994
 * Explanation: DTO for the name of the user
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NameRequestDTO {
    private String name;
}
