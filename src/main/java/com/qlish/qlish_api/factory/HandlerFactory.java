package com.qlish.qlish_api.factory;

import com.qlish.qlish_api.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class HandlerFactory {

    private final Map<String, Handler> promptMap;

    public Handler getHandler(String promptHandlerName){
        var promptHandler = promptMap.get(promptHandlerName);
        if(promptHandler == null){
            throw new IllegalArgumentException("Invalid prompt handler name: " + promptHandlerName);
        }
        return promptHandler;

    }



}
