package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.Question;

import java.util.List;
import java.util.Map;

public interface TestFactory <T extends Question> {
  List<T> getQuestions(Map<String, String> modifiers, int size);
}
