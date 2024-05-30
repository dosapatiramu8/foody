package com.foody.admin.controller;

import com.foody.admin.service.AdminUserService;
import com.foody.common.model.request.admin.AdminUserRequest;
import com.foody.common.model.response.admin.AdminUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin-user")
@AllArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AdminUserResponse> registerAdminUser(@RequestBody AdminUserRequest adminUserRequest) {
        return adminUserService.registerAdminUser(adminUserRequest);
    }

    @GetMapping("/{id}")
    public Mono<AdminUserResponse> getAdminUser(@PathVariable Long id) {
        return adminUserService.getAdminUserById(id);
    }

    @PutMapping("/{id}")
    public Mono<AdminUserResponse> updateAdminUser(@PathVariable Long id, @RequestBody AdminUserRequest adminUserRequest) {
        return adminUserService.updateAdminUser(id, adminUserRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAdminUser(@PathVariable Long id) {
        return adminUserService.deleteAdminUser(id);
    }
}
