package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeaderboardEntry {
    private String profileName;
    private Integer points;
}
