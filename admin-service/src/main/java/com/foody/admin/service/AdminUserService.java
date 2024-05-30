package com.foody.admin.service;

import com.foody.common.model.request.admin.AdminUserRequest;
import com.foody.common.model.response.admin.AdminUserResponse;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;

    public Mono<AdminUserResponse> registerAdminUser(AdminUserRequest adminUserRequest) {
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(adminUserRequest.getUsername());
        adminUser.setEmail(adminUserRequest.getEmail());
        adminUser.setPassword(adminUserRequest.getPassword());

        return adminUserRepository.save(adminUser)
            .map(au -> new AdminUserResponse(
                au.getId(),
                au.getUsername(),
                au.getEmail()
            ));
    }

    public Mono<AdminUserResponse> getAdminUserById(Long id) {
        return adminUserRepository.findById(id)
            .map(au -> new AdminUserResponse(
                au.getId(),
                au.getUsername(),
                au.getEmail()
            ));
    }

    public Mono<AdminUserResponse> updateAdminUser(Long id, AdminUserRequest adminUserRequest) {
        return adminUserRepository.findById(id)
            .flatMap(existingAdminUser -> {
                existingAdminUser.setUsername(adminUserRequest.getUsername());
                existingAdminUser.setEmail(adminUserRequest.getEmail());
                existingAdminUser.setPassword(adminUserRequest.getPassword());
                return adminUserRepository.save(existingAdminUser);
            })
            .map(au -> new AdminUserResponse(
                au.getId(),
                au.getUsername(),
                au.getEmail()
            ));
    }

    public Mono<Void> deleteAdminUser(Long id) {
        return adminUserRepository.deleteById(id);
    }
}
