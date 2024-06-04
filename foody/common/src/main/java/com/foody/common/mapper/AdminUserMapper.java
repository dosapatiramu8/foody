package com.foody.common.mapper;

import com.foody.common.model.request.admin.AdminUserRequest;
import com.foody.common.model.response.admin.AdminUserResponse;
import com.foody.data.entity.admin.AdminUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AdminUserMapper {
    
    public AdminUser convertToAdminUser(AdminUserRequest adminUserRequest){
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminUserRequest, adminUser);
        return adminUser;
    }

    public AdminUserResponse convertToAdminUserResponse(AdminUser adminUser){
        AdminUserResponse adminUserResponse = new AdminUserResponse();
        BeanUtils.copyProperties(adminUser, adminUserResponse);
        return adminUserResponse;
    }
}
