package com.qlish.qlish_api.security.authenticaton;

import com.qlish.qlish_api.security.configuration.LogoutService;
import com.qlish.qlish_api.constants.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;

@RequestMapping(AppConstants.BASE_URL + "/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/login")
    public ResponseEntity<String> getLoginPage() {
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Login</title>
                </head>
                <body>
                    <h2>Login</h2>
                    <form id="loginForm">
                        <label for="email">Email:</label>
                        <input type="text" id="email" name="email" required><br><br>
                        <label for="password">Password:</label>
                        <input type="password" id="password" name="password" required><br><br>
                        <button type="button" onclick="submitLogin()">Login</button>
                    </form>
                    <hr>
                    <button id="googleSignInButton">Sign in with Google</button>
                    <script>
                        function submitLogin() {
                            const email = document.getElementById('email').value;
                            const password = document.getElementById('password').value;

                            const requestBody = {
                                email: email,
                                password: password
                            };

                            fetch('http://localhost:8080/api/v1/auth/login', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify(requestBody)
                            })
                            .then(response => response.json())
                            .then(data => {
                                // Handle successful authentication
                                console.log('Success:', data);
                                // Redirect to a new page or show user info
                            })
                            .catch((error) => {
                                console.error('Error:', error);
                                // Handle authentication error
                            });
                        }

                        document.getElementById('googleSignInButton').onclick = function() {
                            window.location.href = '/oauth2/authorization/google';
                        };
                    </script>
                </body>
                </html>
                """;

        return ResponseEntity.ok().body(html);
    }


    @PostMapping("/refresh")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshAccessToken(request, response);
        response.getOutputStream();
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
    }
}

