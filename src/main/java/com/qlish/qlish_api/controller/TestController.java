package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.dto.TestRequest;
import com.qlish.qlish_api.entity.TestResult;
import com.qlish.qlish_api.service.TestService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    @GetMapping("/new")
    public ResponseEntity<List<String>> takeATest(){
        var subjectList = testService.takeATest();
        return ResponseEntity.ok(subjectList);
    }

    @PostMapping("create/english")
    public ResponseEntity<ObjectId> createEnglishTest(@RequestBody TestRequest request) {

        var testId = testService.createEnglishTest(request);
        return ResponseEntity.ok(testId);
    }

    @GetMapping("start/english")
    public ResponseEntity<TestResult> startEnglishTest () {
        return null;
    }


}
