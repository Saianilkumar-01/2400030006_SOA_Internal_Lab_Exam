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
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getAdminDashboard(Principal principal, Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Welcome to Admin Management Dashboard! Access granted to ADMIN role.");
        response.put("authenticatedUser", principal != null ? principal.getName() : "anonymous");
        response.put("authorities", authentication != null ? authentication.getAuthorities() : null);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/manage-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> manageUsers() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "User management records retrieved successfully.");
        response.put("totalUsers", 2);
        response.put("users", Arrays.asList(
            Map.of("username", "admin", "roles", Arrays.asList("ROLE_ADMIN", "ROLE_USER"), "status", "ACTIVE"),
            Map.of("username", "user", "roles", Arrays.asList("ROLE_USER"), "status", "ACTIVE")
        ));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/system-info")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("service", "Enterprise RBAC Security Service");
        response.put("environment", "Production / Enterprise");
        response.put("securityPolicy", "Role-Based Access Control (RBAC)");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/settings")
    public ResponseEntity<Map<String, Object>> updateAdminSettings(@RequestBody(required = false) Map<String, Object> settings) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "System management settings updated by Administrator.");
        response.put("appliedSettings", settings != null ? settings : "Default configuration applied");
        return ResponseEntity.ok(response);
    }
}
