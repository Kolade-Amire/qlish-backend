package com.qlish.qlish_api.service;

import com.qlish.qlish_api.dto.LeaderboardEntry;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.exception.LeaderboardException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.qlish.qlish_api.constants.AppConstants.ALL_TIME_LEADERBOARD_KEY;

@RequiredArgsConstructor
@Service
public class AllTimeLeaderboardService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(AllTimeLeaderboardService.class);

    private final UserService userService;


    public void updateLeaderboard(LeaderboardEntry entry) {
        Double minScore = getMinimumScore();

        if (minScore == null || entry.getPoints() > minScore) {

            // Check if user exists in leaderboard
            Double existingScore = redisTemplate.opsForZSet().score(ALL_TIME_LEADERBOARD_KEY, entry.getProfileName());

            if (existingScore != null) {
                // If user already exists, increment their score
                redisTemplate.opsForZSet().incrementScore(ALL_TIME_LEADERBOARD_KEY, entry.getProfileName(), entry.getPoints());
            } else {
                // If user doesn't exist, add to leaderboard
                redisTemplate.opsForZSet().add(ALL_TIME_LEADERBOARD_KEY, entry.getProfileName(), entry.getPoints());

                // Remove the lowest-ranked user if the leaderboard exceeds 20 entries
                trimLeaderboard();
            }
        }
    }

    //Retrieves the top 20 users from the all-time leaderboard.
    public List<LeaderboardEntry> getAllTimeLeaderboard() {
        try {
            var leaderboardSet = redisTemplate.opsForZSet().reverseRangeWithScores(ALL_TIME_LEADERBOARD_KEY, 0, 19);

            if (leaderboardSet == null || leaderboardSet.isEmpty()) {
                return Collections.emptyList();
            }
            return leaderboardSet.stream()
                    .filter(entry -> entry.getValue() != null && entry.getScore() != null)
                    .map(
                            entry -> LeaderboardEntry.builder()
                                    .profileName(entry.getValue().toString())
                                    .points(entry.getScore().longValue())
                                    .build()
                    ).toList();


        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new LeaderboardException("Error occurred. Unable to retrieve all-time leaderboard", e);
        }


    }

    /**
     * Periodic task to synchronize Redis leaderboard with MongoDB.
     * Ensures consistency and handles edge cases like missed updates.
     */
    @Scheduled(cron = "0 0 0,12 * * ?", zone = "UTC") // Runs twice daily, at 12 morning and noon
    public void synchronizeLeaderboardWithDatabase() {
        try {
            LOGGER.info("Synchronizing Redis leaderboard with MongoDB...");

            var topUsersFromDB = userService.getUsersWithTop20Points();

            // Clear Redis leaderboard
            redisTemplate.delete(ALL_TIME_LEADERBOARD_KEY);

            // Repopulate Redis leaderboard with top users from MongoDB
            for (var user : topUsersFromDB) {
                redisTemplate.opsForZSet().add(ALL_TIME_LEADERBOARD_KEY, user.getProfileName(), user.getAllTimePoints());
            }

            LOGGER.info("Redis leaderboard synchronized successfully.");
        } catch (Exception e) {
            throw new CustomQlishException("An error occurred while synchronizing leaderboard", e);
        }
    }

    private Double getMinimumScore() {
        Set<ZSetOperations.TypedTuple<Object>> entries = redisTemplate.opsForZSet().rangeWithScores(ALL_TIME_LEADERBOARD_KEY, 0, 0);
        if (entries != null && !entries.isEmpty()) {
            return entries.iterator().next().getScore();
        }
        return null;
    }

    //Trims the leaderboard to retain only the top 20 users
    private void trimLeaderboard() {
        Long leaderboardSize = redisTemplate.opsForZSet().size(ALL_TIME_LEADERBOARD_KEY);
        if (leaderboardSize != null && leaderboardSize > 20) {
            redisTemplate.opsForZSet().popMin(ALL_TIME_LEADERBOARD_KEY);
        }
    }
}


