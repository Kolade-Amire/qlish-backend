package com.qlish.qlish_api.service;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.dto.LeaderboardEntry;
import com.qlish.qlish_api.exception.LeaderboardException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DailyLeaderboardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyLeaderboardService.class);


    private final RedisTemplate<String, Object> redisTemplate;

    //updates daily points for an entry in the leaderboard
    public void updateDailyScore(LeaderboardEntry entry) {
        String key = getDailyLeaderboardKey();
        Boolean isKeyPresent = redisTemplate.hasKey(key);
        redisTemplate.opsForZSet().incrementScore(key, entry.getProfileName(), entry.getPoints());

        if(Boolean.FALSE.equals(isKeyPresent)) {
            setLeaderboardKeyExpiration(key);
        }
    }

    //Gets top ten users for daily leaderboard entry
    public List<LeaderboardEntry> getTopDailyPoints() {
        String key = getDailyLeaderboardKey();

        try {
            Set<ZSetOperations.TypedTuple<Object>> topEntries = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 19);

            if (topEntries == null) {
                throw new NullPointerException("Cannot retrieve daily leaderboard. Leaderboard set is null.");
            }
            //map results to leaderboard entry objects
            return topEntries.stream()
                    .map(tuple ->
                            {
                                try {
                                    if (tuple.getValue() != null && tuple.getScore() != null) {
                                        return new LeaderboardEntry(
                                                tuple.getValue().toString(),
                                                tuple.getScore().intValue()
                                        );
                                    }
                                    throw new RuntimeException("Error occurred while trying to retrieve daily leaderboard entry.");
                                } catch (Exception e) {
                                    LOGGER.error(e.getMessage());
                                    throw new LeaderboardException(e.getMessage());
                                }
                            }
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new LeaderboardException("An error occurred. Unable to retrieve daily leaderboard.");
        }

    }

    private String getDailyLeaderboardKey() {
        String date = LocalDate.now(ZoneOffset.ofHours(1)).toString();
        return AppConstants.DAILY_LEADERBOARD_KEY_PREFIX + date;
    }

    public void setLeaderboardKeyExpiration(String key) {
        // Calculate time to midnight
        LocalDateTime now = LocalDateTime.now(ZoneOffset.ofHours(1));
        LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1);
        long secondsToMidnight = Duration.between(now, midnight).getSeconds();
        redisTemplate.expire(key, secondsToMidnight, TimeUnit.SECONDS);
    }

}
