package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;


    @PostMapping("create/english")
    public ResponseEntity<ObjectId> createEnglishTest(@RequestBody TestRequest request) {

        var testId = testService.createEnglishTest(request);
        return ResponseEntity.ok(testId);
    }

    @PostMapping
    public ResponseEntity<TestResult> startEnglishTest () {
        return null;
    }


}
