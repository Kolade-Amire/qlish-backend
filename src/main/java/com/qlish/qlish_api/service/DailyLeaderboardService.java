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

import static com.qlish.qlish_api.constants.AppConstants.ALL_TIME_LEADERBOARD_KEY;

@Service
@RequiredArgsConstructor
public class DailyLeaderboardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyLeaderboardService.class);


    private final RedisTemplate<String, Object> redisTemplate;

    //updates daily points for an entry in the leaderboard
    public void updateDailyScore(LeaderboardEntry entry) {

        //if key doesn't exist, set expiration for the new key
        String key = getDailyLeaderboardKey();
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            setLeaderboardKeyExpiration(key);
        }

        //update leaderboard with entry

        Double minimumScore = getMinimumScore();

        if (minimumScore == null || entry.getPoints() > minimumScore) {

            //check if user exists in leaderboard
            Double existingScore = redisTemplate.opsForZSet().score(key, entry.getProfileName());

            //update score if exists
            if (existingScore != null) {
                redisTemplate.opsForZSet().incrementScore(key, entry.getProfileName(), entry.getPoints());
            } else {
                // add entry if not exists
                redisTemplate.opsForZSet().add(key, entry.getProfileName(), entry.getPoints());

                trimLeaderboard();
            }
        }


        redisTemplate.opsForZSet().incrementScore(key, entry.getProfileName(), entry.getPoints());
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
                    .filter(entry -> entry.getValue() != null && entry.getScore() != null)
                    .map(entry ->
                            LeaderboardEntry.builder()
                                    .points(entry.getScore().longValue())
                                    .profileName(entry.getValue().toString())
                                    .build()
                    ).toList();

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new LeaderboardException("Error occurred. Unable to retrieve daily leaderboard", e);
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

    private Double getMinimumScore() {
        Set<ZSetOperations.TypedTuple<Object>> entries = redisTemplate.opsForZSet().rangeWithScores(getDailyLeaderboardKey(), 0, 0);
        if (entries != null && !entries.isEmpty()) {
            return entries.iterator().next().getScore();
        }
        return null;
    }

    //Trims the leaderboard to retain only the top 20 users
    private void trimLeaderboard() {
        Long leaderboardSize = redisTemplate.opsForZSet().size(getDailyLeaderboardKey());
        if (leaderboardSize != null && leaderboardSize > 20) {
            redisTemplate.opsForZSet().popMin(getDailyLeaderboardKey());
        }
    }

}
