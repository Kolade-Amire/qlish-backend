package com.qlish.qlish_api.util;

import com.qlish.qlish_api.constants.TestSubject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public abstract class TestFactory {

    @Setter
    @Getter
    protected String subject;

    @Setter
    @Getter
    protected int questionCount;


    public void validateTest(){
        if (!TestSubject.isValidSubjectName(subject)) {
            throw new IllegalArgumentException("Subject name is invalid");
        }
    }

    protected Map<String, String> modifiers = new HashMap<>();

    public void addModifier(String key, String value) {
        modifiers.put(key, value);
    }

    public String getModifier(String key) {
        return modifiers.get(key);
    }

    public Map<String, String> getAllModifiers() {
        return modifiers;
    }




}
