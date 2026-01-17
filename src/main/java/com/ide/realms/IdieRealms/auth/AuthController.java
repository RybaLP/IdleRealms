package com.ide.realms.IdieRealms.auth;

import com.ide.realms.IdieRealms.auth.dto.LoginRequest;
import com.ide.realms.IdieRealms.auth.dto.RegisterRequest;
import jakarta.persistence.PreUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/register")
    public ResponseEntity<Map<String,String>> registerAccount (@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerPlayer(registerRequest);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Account registered successfully");
        response.put("success", "true");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginAccount (@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.loginPlayer(loginRequest);

        ResponseCookie cookie = ResponseCookie.from("jwt" , token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .secure(false)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged in");
    }
}