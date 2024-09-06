package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public List<TestEntity> findTestsByUserId(ObjectId userId) {
        return testRepository.findAllByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("User has not taken any test.")
        );
    }

    @Override
    public TestEntity findTestById(ObjectId id) {
        return testRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Test not found.")
        );
    }

    @Override
    public void save(TestEntity testEntity) {
        testRepository.save(testEntity);
    }

    @Override
    public void delete(TestEntity testEntity) {
        testRepository.delete(testEntity);
    }
}
