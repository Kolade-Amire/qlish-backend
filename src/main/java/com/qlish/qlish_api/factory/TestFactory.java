package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.entity.TestDetails;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.strategy.QuestionRetrievalStrategy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class TestFactory {

    private final <? extends QuestionModifier> modifier;

    public static TestEntity createTest(ObjectId userId, TestSubject subject, int questionCount) {
        QuestionRetrievalStrategy<modifier> strategy = QuestionRetrievalStrategyFactory.getStrategy(subject);
        List<? extends Question> questions = strategy.getQuestions(modifier, questionCount);

        var testQuestions = QuestionMapper.mapQuestionListToTestDto(questions);

        var testDetails = TestDetails.builder()
                .userId(userId)
                .testSubject(subject)
                .startedAt(LocalDateTime.now())
                .totalQuestionCount(questionCount)
                .isCompleted(false)
                .build();

        return TestEntity.builder()
                .testDetails(testDetails)
                .questions(questions)
                .build();
    }


}
