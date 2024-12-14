package com.qlish.qlish_api.service;

import com.qlish.qlish_api.model.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.qlish.qlish_api.constants.AppConstants.ALL_TIME_LEADERBOARD_KEY;

@RequiredArgsConstructor
@Service
public class AllTimeLeaderboardService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final UserService userService;


    public void updateAllTimePoints(String userId, int points){
        var _id = new ObjectId(userId);
        userService.updateUserAllTimePoints(_id, points);
        refreshLeaderboardCache();
    }

    public void updateAllTimeLeaderboard(String userId, String profileName, long newPoints) {
        // Step 1: Get the 20th user's score
        Double minScore = getMinimumScore();

        if (minScore == null || newPoints > minScore) {
            // Step 2: Update MongoDB
            userRepository.incrementAllTimePoints(userId, newPoints);

            // Step 3: Check if user exists in Redis leaderboard
            Double existingScore = redisTemplate.opsForZSet().score(ALL_TIME_LEADERBOARD_KEY, profileName);

            if (existingScore != null) {
                // If user already exists, increment their score
                redisTemplate.opsForZSet().incrementScore(ALL_TIME_LEADERBOARD_KEY, profileName, newPoints);
            } else {
                // If user doesn't exist, add to Redis leaderboard
                redisTemplate.opsForZSet().add(ALL_TIME_LEADERBOARD_KEY, profileName, newPoints);

                // Remove the lowest-ranked user if the leaderboard exceeds 20 entries
                trimLeaderboardToTopN(20);
            }
        }
    }

    /**
     * Retrieves the top 20 users from the all-time leaderboard.
     *
     * @return a set of leaderboard entries with scores
     */
    public Set<ZSetOperations.TypedTuple<Object>> getAllTimeLeaderboard() {
        return redisTemplate.opsForZSet().reverseRangeWithScores(ALL_TIME_LEADERBOARD_KEY, 0, 19);
    }

    /**
     * Periodic task to synchronize Redis leaderboard with MongoDB.
     * Ensures consistency and handles edge cases like missed updates.
     */
    @Scheduled(cron = "0 0 * * * ?") // Run hourly
    public void synchronizeLeaderboardWithDatabase() {
        log.info("Synchronizing Redis leaderboard with MongoDB...");

        // Fetch top 20 users from MongoDB
        var topUsersFromDB = userService.findTop20ByOrderByAllTimePointsDesc();

        // Clear Redis leaderboard
        redisTemplate.delete(ALL_TIME_LEADERBOARD_KEY);

        // Repopulate Redis leaderboard with top users from MongoDB
        for (var user : topUsersFromDB) {
            redisTemplate.opsForZSet().add(ALL_TIME_LEADERBOARD_KEY, user.getProfileName(), user.getAllTimePoints());
        }

        log.info("Redis leaderboard synchronized successfully.");
    }

    /**
     * Retrieves the minimum score (20th rank) in the leaderboard.
     *
     * @return the minimum score or null if the leaderboard is empty
     */
    private Double getMinimumScore() {
        Set<ZSetOperations.TypedTuple<Object>> entries = redisTemplate.opsForZSet().rangeWithScores(ALL_TIME_LEADERBOARD_KEY, 19, 19);
        if (entries != null && !entries.isEmpty()) {
            return entries.iterator().next().getScore();
        }
        return null;
    }

    /**
     * Trims the Redis leaderboard to retain only the top N users.
     *
     * @param topN the number of top users to retain
     */
    private void trimLeaderboardToTopN(int topN) {
        Long leaderboardSize = redisTemplate.opsForZSet().size(ALL_TIME_LEADERBOARD_KEY);
        if (leaderboardSize != null && leaderboardSize > topN) {
            redisTemplate.opsForZSet().removeRange(ALL_TIME_LEADERBOARD_KEY, 0, leaderboardSize - topN - 1);
        }
    }
}

}
