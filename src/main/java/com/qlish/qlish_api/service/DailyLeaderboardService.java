package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.LeaderboardEntry;
import com.qlish.qlish_api.exception.CustomQlishException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyLeaderboardService {
    private static final String DAILY_LEADERBOARD_KEY_PREFIX = "daily_leaderboard:";

    private final RedisTemplate<String, Object> redisTemplate;

    //updates daily points for an entry in the leaderboard
    public void updateDailyScore(LeaderboardEntry entry) {
        String key = getDailyLeaderboardKey();
        redisTemplate.opsForZSet().incrementScore(key, entry.getProfileName(), entry.getPoints());
    }

    //Gets top ten users for daily leaderboard entry
    public List<LeaderboardEntry> getTopDailyPoints() {
        String key = getDailyLeaderboardKey();

        try {
            Set<ZSetOperations.TypedTuple<Object>> topEntries = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 9);

            if(topEntries == null){
                throw new NullPointerException("Cannot retrieve daily leaderboard. Leaderboard set is null.");
            }
            //map results to leaderboard entry objects
            return topEntries.stream()
                    .map(tuple ->
                            new LeaderboardEntry(
                                    tuple.getValue().toString(),
                                    tuple.getScore().longValue()
                            )
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomQlishException("");
        }

    }

    private String getDailyLeaderboardKey() {
        String date = LocalDate.now(ZoneOffset.ofHours(1)).toString();
        return DAILY_LEADERBOARD_KEY_PREFIX + date;
    }

    public void resetDailyLeaderboard() {
        String key = getDailyLeaderboardKey();
        redisTemplate.delete(key);
    }

}
