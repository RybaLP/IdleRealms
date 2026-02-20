package com.ide.realms.IdieRealms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "Root", description = "Main entry point and system status")
public class RootController {

    @GetMapping("/")
    @Operation(summary = "System status", description = "Simple endpoint to check if the API is running.")
    public Map<String, Object> getStatus() {
        return Map.of(
                "project", "Idle Realms API",
                "status", "Running",
                "version", "1.0.4",
                "serverTime", LocalDateTime.now(),
                "documentation", "/swagger-ui.html"
        );
    }

}
