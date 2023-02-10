package com.gianca1994.aowebbackend.resources.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Gianca1994
 * Explanation: DTO for the ranking response
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponseDTO {
    private List<UserRankingDTO> ranking;
    private int totalPages;
}
