package com.qlish.qlish_api.enums.english;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EnglishTopic {

    ANTONYMS("antonyms"),
    PARAGRAPH("paragraph"),
    PHRASAL_VERB("phrasal-verb"),
    READING_COMPREHENSION("reading-comprehension"),
    SENTENCE_COMPLETION("sentence-completion"),
    SYNONYMS("synonyms"),
    PARTS_OF_SPEECH("parts-of-speech"),
    TENSES("tenses");

    private final String topicName;
    public static EnglishTopic fromTopicName(String topicName) {
        for (EnglishTopic topic : EnglishTopic.values()) {
            if (topic.getTopicName().equalsIgnoreCase(topicName)) {
                return topic;
            }
        }
        return null;
    }
}
