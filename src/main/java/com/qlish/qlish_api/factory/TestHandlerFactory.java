package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.generativeAI.TestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class TestHandlerFactory {

    private final Map<String, TestHandler> promptMap;

    public TestHandler getTestHandler(String promptHandlerName){
        var promptHandler = promptMap.get(promptHandlerName);
        if(promptHandler == null){
            throw new IllegalArgumentException("Invalid prompt handler name: " + promptHandlerName);
        }
        return promptHandler;

    }



}
