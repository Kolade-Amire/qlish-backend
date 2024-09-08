package com.qlish.qlish_api.english_question;

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
}