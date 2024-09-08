package com.qlish.qlish_api.practice_test;

import java.util.HashMap;
import java.util.Map;

public abstract class TestModifier {

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
