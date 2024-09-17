package com.qlish.qlish_api.enums.english_enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnglishQuestionTopic {

    ANTONYMS("antonyms"),
    PARAGRAPH("paragraph"),
    PHRASAL_VERB("phrasal-verb"),
    READING_COMPREHENSION("reading-comprehension"),
    SENTENCE_COMPLETION("sentence-completion"),
    SYNONYMS("synonyms"),
    PARTS_OF_SPEECH("parts-of-speech"),
    TENSES("tenses");

    private final String topicName;

    public static EnglishQuestionTopic fromTopicName(String topicName) {
        for (EnglishQuestionTopic topic : EnglishQuestionTopic.values()) {
            if (topic.getTopicName().equalsIgnoreCase(topicName)) {
                return topic;
            }
        }
        throw new IllegalArgumentException("Invalid level name: " + topicName);
    }
}
