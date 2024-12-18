package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.LeaderboardEntry;
import com.qlish.qlish_api.service.AllTimeLeaderboardService;
import com.qlish.qlish_api.service.DailyLeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/leaderboard")
public class LeaderBoardController {

    private final AllTimeLeaderboardService allTimeLeaderboardService;
    private final DailyLeaderboardService dailyLeaderboardService;

    @GetMapping("/all-time")
    public ResponseEntity<List<LeaderboardEntry>> getAllTimeLeaderboard(){
        var leaderboard = allTimeLeaderboardService.getAllTimeLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    @GetMapping("/daily")
    public ResponseEntity<List<LeaderboardEntry>> getDailyLeaderboard(){
        var leaderboard = dailyLeaderboardService.getTopDailyPoints();
        return ResponseEntity.ok(leaderboard);
    }
}
