package com.qlish.qlish_api.question;

import java.util.List;


public interface CustomQuestionRepository {
    List<QuestionEntity> findQuestionsByCriteria(String questionLevel, String questionClass, String questionTopic);
}
