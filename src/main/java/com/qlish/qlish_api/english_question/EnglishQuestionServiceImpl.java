package com.qlish.qlish_api.english_question;

import com.qlish.qlish_api.exception.QuestionsRetrievalException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnglishQuestionServiceImpl implements EnglishQuestionService {

    Logger logger = LoggerFactory.getLogger(EnglishQuestionServiceImpl.class);

    private final EnglishQuestionRepository repository;


    @Override
    public Page<EnglishQuestionEntity> getEnglishQuestions(Pageable pageable, EnglishQuestionLevel questionEnglishQuestionLevel, EnglishQuestionClass englishQuestionClass, EnglishQuestionTopic questionEnglishQuestionTopic, int testSize) {

        if (testSize < 10) {
            throw new IllegalArgumentException("Test size must be at least 10.");
        }

        try {
            return repository.findQuestionsByCriteria(pageable, questionEnglishQuestionLevel, englishQuestionClass, questionEnglishQuestionTopic, testSize);
        } catch (Exception e) {
            logger.error("Error retrieving questions from repository");
            throw new QuestionsRetrievalException(e.getMessage());
        }

    }
}
