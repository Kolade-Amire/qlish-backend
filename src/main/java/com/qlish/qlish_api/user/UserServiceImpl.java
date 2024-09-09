package com.qlish.qlish_api.user;

import com.mongodb.MongoTimeoutException;
import com.mongodb.MongoWriteException;
import com.qlish.qlish_api.exception.CustomDatabaseException;
import com.qlish.qlish_api.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(AppConstants.USER_NOT_FOUND));
    }


    @Override
    public UserEntity saveUser(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (
    MongoWriteException e) {
        logger.error("Mongo write error: {}", e.getMessage());
        throw new CustomDatabaseException("Error writing to MongoDB: " + e.getMessage(), e);
    } catch (
    MongoTimeoutException e) {
        logger.error("Mongo timeout error: {}", e.getMessage());
        throw new CustomDatabaseException("MongoDB connection timeout: " + e.getMessage(), e);
    } catch (
    DataAccessException e) {
        logger.error("Data access error: {}", e.getMessage());
        throw new CustomDatabaseException("Data access error: " + e.getMessage(), e);
    } catch (Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage());
        throw new CustomDatabaseException("Unexpected error occurred while saving entity: " + e.getMessage(), e);
    }
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
