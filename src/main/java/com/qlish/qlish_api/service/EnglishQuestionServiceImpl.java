package com.qlish.qlish_api.service;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.EnglishQuestionEntity;
import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import com.qlish.qlish_api.repository.EnglishQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnglishQuestionServiceImpl implements EnglishQuestionService {

    Logger logger = LoggerFactory.getLogger(EnglishQuestionServiceImpl.class);

    private final EnglishQuestionRepository repository;


    @Override
    public List<EnglishQuestionEntity> getEnglishQuestions(EnglishModifier modifier, int testSize) {

        if (testSize < 10) {
            throw new IllegalArgumentException("Test size must be at least 10.");
        }
        try {
            return repository.findQuestionsByCriteria(modifier , testSize);
        } catch (Exception e) {
            logger.error("Error retrieving questions from repository");
            throw new QuestionsRetrievalException(e.getMessage());
        }

    }
}
