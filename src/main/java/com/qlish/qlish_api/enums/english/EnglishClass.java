package com.qlish.qlish_api.enums.english;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnglishClass {

    GRAMMAR("grammar"),
    VOCABULARY("vocabulary");

    private final String className;

    public static EnglishClass fromClassName(String className) {
        for (EnglishClass item : EnglishClass.values()) {
            if (item.getClassName().equalsIgnoreCase(className)) {
                return item;
            }
        }
        return null;
    }

}
