package com.qlish.qlish_api.generativeAI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qlish.qlish_api.exception.GenerativeAIException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class GeminiAI {

    private static final Logger logger = LoggerFactory.getLogger(GeminiAI.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long INITIAL_RETRY_DELAY_MS = 1000;

    @Value("${api.endpoint}")
    private String apiEndpoint;
    @Value("${api.key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();


    public String generateQuestions(String prompt, String systemInstruction) throws GenerativeAIException {

        objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        var jsonRequestBody = buildRequestBody(prompt, systemInstruction);

        // Create the request with the JSON payload
        RequestBody body = RequestBody.create(
                jsonRequestBody.getBytes(StandardCharsets.UTF_8),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(apiEndpoint + "?key=" + this.apiKey)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            return executeRequest(request);
        } catch (IOException e) {
            throw new GenerativeAIException("An error occurred while executing request to generate questions.");
        }
    }

    private String buildRequestBody(String prompt, String systemInstruction) {
        Map<String, Object> requestBody = new HashMap<>();

        //build the system_instructions parts
        Map<String, Object> instructions = new HashMap<>();
        instructions.put("parts", Map.of("text", systemInstruction));
        requestBody.put("system_instruction", instructions);

        // Create the contents part with the prompt
        Map<String, Object> contents = new HashMap<>();
        contents.put("parts", List.of(Map.of("text", prompt)));
        requestBody.put("contents", List.of(contents));


        try {
            return objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body: ", e);
        }

    }

    private String executeRequest(Request request) throws IOException {
        int attempt = 0;
        long retryDelay = INITIAL_RETRY_DELAY_MS;

        while (attempt < MAX_RETRY_ATTEMPTS) {
            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new IOException("Request failed with code: " + response.code() + " and message: " + response.message());
                }
                String responseBody = response.body().string();
                logger.debug("Received response: {}", responseBody);
                return responseBody;
            } catch (IOException e) {
                attempt++;
                if (attempt >= MAX_RETRY_ATTEMPTS) {
                    logger.error("All retry attempts failed for request: {}", request, e);
                    throw e;
                }
                logger.warn("Attempt {} failed, retrying after {} ms...", attempt, retryDelay, e);
                try {
                    Thread.sleep(retryDelay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Re-interrupt the thread
                    throw new IOException("Retry interrupted", ie);
                }
                retryDelay *= 2; // Exponential backoff
            }
        }
        throw new IOException("Unable to complete request after multiple retries");
    }


}
