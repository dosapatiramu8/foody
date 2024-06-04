package com.foody.admin.service;

import com.foody.common.mapper.AdminUserMapper;
import com.foody.common.model.request.admin.AdminUserRequest;
import com.foody.common.model.response.admin.AdminUserResponse;
import com.foody.data.repository.admin.AdminUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AdminUserService {

    private final AdminUserRepository adminUserRepository;

    private final AdminUserMapper adminUserMapper;

    public Mono<AdminUserResponse> registerAdminUser(AdminUserRequest adminUserRequest) {
        return adminUserRepository.save(adminUserMapper.convertToAdminUser(adminUserRequest))
                .map(adminUserMapper::convertToAdminUserResponse);
    }

    public Mono<AdminUserResponse> getAdminUserById(String id) {
        return adminUserRepository.findById(id).map(adminUserMapper::convertToAdminUserResponse);
    }

    public Mono<AdminUserResponse> updateAdminUser(AdminUserRequest adminUserRequest) {
        return adminUserRepository.update(adminUserMapper.convertToAdminUser(adminUserRequest))
                .map(adminUserMapper::convertToAdminUserResponse);
    }

    public Mono<Void> deleteAdminUser(String id) {
        return adminUserRepository.deleteById(id);
    }
}

