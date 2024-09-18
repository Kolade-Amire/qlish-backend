package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.entity.Question;
import com.qlish.qlish_api.entity.QuestionModifier;
import com.qlish.qlish_api.entity.TestDetails;
import com.qlish.qlish_api.entity.TestEntity;
import com.qlish.qlish_api.enums.TestSubject;
import com.qlish.qlish_api.mapper.QuestionMapper;
import com.qlish.qlish_api.strategy.QuestionRetrievalStrategy;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class TestFactory {

    private final QuestionRetrievalFactory retrievalFactory;
    private final ModifierFactory<? extends QuestionModifier> modifierFactory;


    public TestEntity createTest(TestRequest testRequest) {

        var testSubject = TestSubject.getSubjectByDisplayName(testRequest.getTestSubject());

        QuestionRetrievalStrategy strategy = retrievalFactory.getStrategy(testSubject);


        var modifier = modifierFactory.getModifier(testSubject, testRequest.getModifier());


        List<? extends Question> questions = strategy.getQuestions(modifier, testRequest.getQuestionCount());

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
