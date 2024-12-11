package com.qlish.qlish_api.service;

import com.qlish.qlish_api.model.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private void refreshLeaderboardCache() {
        List<User> topUsers = userService.get
    }
}
