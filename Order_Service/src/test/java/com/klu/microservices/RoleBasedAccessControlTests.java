package com.klu.microservices;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleBasedAccessControlTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Public endpoint /api/public/info should be accessible without authentication (200 OK)")
    void testPublicEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/public/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    @DisplayName("Admin endpoint /api/admin/dashboard without credentials should return 401 Unauthorized")
    void testAdminEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @DisplayName("Admin endpoint /api/admin/dashboard with USER credentials should return 403 Forbidden")
    void testAdminEndpointWithUserCredentialsForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("user", "user123")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.error").value("Forbidden"));
    }

    @Test
    @DisplayName("Admin endpoint /api/admin/dashboard with ADMIN credentials should return 200 OK")
    void testAdminEndpointWithAdminCredentialsSuccess() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.authenticatedUser").value("admin"));
    }

    @Test
    @DisplayName("Admin endpoint /api/admin/manage-users with ADMIN credentials should return 200 OK")
    void testAdminManageUsersWithAdminSuccess() throws Exception {
        mockMvc.perform(get("/api/admin/manage-users").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.totalUsers").value(2));
    }

    @Test
    @DisplayName("Admin endpoint /api/admin/manage-users with USER credentials should return 403 Forbidden")
    void testAdminManageUsersWithUserForbidden() throws Exception {
        mockMvc.perform(get("/api/admin/manage-users").with(httpBasic("user", "user123")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    @DisplayName("User endpoint /api/user/profile without credentials should return 401 Unauthorized")
    void testUserEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/user/profile"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @DisplayName("User endpoint /api/user/profile with USER credentials should return 200 OK")
    void testUserEndpointWithUserCredentialsSuccess() throws Exception {
        mockMvc.perform(get("/api/user/profile").with(httpBasic("user", "user123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.authenticatedUser").value("user"));
    }

    @Test
    @DisplayName("User endpoint /api/user/profile with ADMIN credentials should return 200 OK")
    void testUserEndpointWithAdminCredentialsSuccess() throws Exception {
        mockMvc.perform(get("/api/user/profile").with(httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.authenticatedUser").value("admin"));
    }

    @Test
    @DisplayName("Request with invalid password should return 401 Unauthorized")
    void testInvalidPassword() throws Exception {
        mockMvc.perform(get("/api/user/profile").with(httpBasic("user", "wrongpassword")))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }
}
