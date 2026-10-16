package com.klu.microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
        System.out.println("==================================================================");
        System.out.println("  Role-Based Access Control (RBAC) Service Started Successfully!  ");
        System.out.println("  Base URL:         http://localhost:8080                         ");
        System.out.println("  Public Endpoint:  GET  http://localhost:8080/api/public/info    ");
        System.out.println("  Admin Endpoints:  GET  http://localhost:8080/api/admin/dashboard");
        System.out.println("  User Endpoints:   GET  http://localhost:8080/api/user/profile  ");
        System.out.println("  Credentials:                                                    ");
        System.out.println("    - Admin: admin / admin123  (Role: ROLE_ADMIN, ROLE_USER)      ");
        System.out.println("    - User:  user  / user123   (Role: ROLE_USER)                  ");
        System.out.println("==================================================================");
    }
}
