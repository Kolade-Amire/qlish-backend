package com.qlish.qlish_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeaderboardEntry {
    private String profileName;
    private Long points;
}
