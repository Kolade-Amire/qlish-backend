package com.qlish.qlish_api.service;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.exception.CustomQlishException;
import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.exception.EntityNotFoundException;
import com.qlish.qlish_api.model.User;
import com.qlish.qlish_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));
    }

    @Override
    public User findUserById(ObjectId id) {
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("User with ID: %s not found", id.toHexString())
        ));
    }


    @Override
    public User saveUser(User user) {
        try {
            return userRepository.save(user);
        } catch (
    MongoWriteException e) {
        logger.error("Mongo write error: {}", e.getMessage());
        throw new CustomQlishException("Error writing to MongoDB: " + e.getMessage(), e.getCause());
    } catch (
    MongoTimeoutException e) {
        logger.error("Mongo timeout error: {}", e.getMessage());
        throw new CustomQlishException("MongoDB connection timeout: " + e.getMessage(), e.getCause());
    } catch (
    DataAccessException e) {
        logger.error("Data access error: {}", e.getMessage());
        throw new CustomQlishException("Data access error: " + e.getMessage(), e.getCause());
    } catch (Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage());
        throw new CustomQlishException("Unexpected error occurred while saving entity: " + e.getMessage(), e.getCause());
    }
    }

    @Override
    public void updateUserAllTimePoints(ObjectId id, int testPoints) {
        var user = findUserById(id);
        var currentTotal = user.getAllTimePoints();
        user.setAllTimePoints(currentTotal + testPoints);
    }

    @Override
    public List<User> getUsersWithTop20Points() {
        return List.of();
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
