package com.qlish.qlish_api.practice_test;

import com.qlish.qlish_api.question.Question;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Builder
@RequiredArgsConstructor
public class TestResponse {

    Page<Question> questions;

}
