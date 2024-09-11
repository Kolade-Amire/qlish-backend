package com.qlish.qlish_api.controller;

import com.qlish.qlish_api.constants.AppConstants;
import com.qlish.qlish_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.BASE_URL + "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard() {

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Login</title>
                </head>
                
                <body>
                    <h1>Successfully logged in!.</h1>
                </body>
                </html>
               
                """;

        return ResponseEntity.ok().body(html);
    }
}
