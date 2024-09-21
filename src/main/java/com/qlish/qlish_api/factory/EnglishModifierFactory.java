package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.entity.EnglishModifier;
import com.qlish.qlish_api.entity.QuestionModifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Qualifier("english")
public class EnglishModifierFactory implements ModifierFactory {

    @Override
    public QuestionModifier createModifier(Map<String, String> requestParams) {
        var level = requestParams.get("level");
        var questionClass = requestParams.get("class");
        var topic = requestParams.get("topic");

        return new EnglishModifier(level, questionClass, topic);
    }
}
