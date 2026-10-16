package com.klu.microservices.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(Principal principal, Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Welcome to User Profile! Access granted to USER role.");
        response.put("authenticatedUser", principal != null ? principal.getName() : "anonymous");
        response.put("authorities", authentication != null ? authentication.getAuthorities() : null);
        response.put("email", (principal != null ? principal.getName() : "user") + "@enterprise.com");
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> getUserDashboard(Principal principal) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "User dashboard retrieved successfully.");
        response.put("username", principal != null ? principal.getName() : "user");
        response.put("assignedTasks", Arrays.asList("Review pending tickets", "Complete weekly survey"));
        response.put("notifications", 3);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resources")
    public ResponseEntity<Map<String, Object>> getUserResources() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "User-specific resources accessed.");
        response.put("availableResources", Arrays.asList("Personal Document Store", "Training Modules", "User Preferences"));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-profile")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody(required = false) Map<String, Object> profileData) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "User profile updated successfully.");
        response.put("data", profileData != null ? profileData : "No payload provided");
        return ResponseEntity.ok(response);
    }
}
