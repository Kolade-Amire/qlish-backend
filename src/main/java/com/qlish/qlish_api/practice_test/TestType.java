package com.qlish.qlish_api.practice_test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestType {
    VOCABULARY("Vocabulary"),
    GRAMMAR("Grammar"),
    RANDOM("Random"),
    ANTONYMS("Antonyms"),
    PARAGRAPH("Paragraphs"),
    PHRASAL_VERB("Phrasal Verbs"),
    READING_COMPREHENSION("Reading Comprehension"),
    SENTENCE_COMPLETION("Sentence Completion"),
    SYNONYMS("Synonyms"),
    PARTS_OF_SPEECH("Parts of Speech"),
    TENSES("Tenses");

    private final String testTypeName;
}
