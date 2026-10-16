package com.klu.microservices.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getPublicInfo() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("application", "Role-Based Access Control (RBAC) API Service");
        response.put("description", "Demonstration of Spring Security Role-Based Authorization for REST API endpoints");
        response.put("publicEndpoints", new String[]{"/api/public/info", "/api/public/welcome"});
        response.put("adminEndpoints", new String[]{"/api/admin/dashboard", "/api/admin/manage-users", "/api/admin/system-info"});
        response.put("userEndpoints", new String[]{"/api/user/profile", "/api/user/dashboard", "/api/user/resources"});
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/welcome")
    public ResponseEntity<Map<String, Object>> welcome() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Welcome to the Enterprise Application. Please authenticate using Basic Auth to access role-protected endpoints.");
        return ResponseEntity.ok(response);
    }
}
