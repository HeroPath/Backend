package com.gianca1994.aowebbackend.resources.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponseDTO {
    private List<UserRankingDTO> ranking;
    private int totalPages;
}
