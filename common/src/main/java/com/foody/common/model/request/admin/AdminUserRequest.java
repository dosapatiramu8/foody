package com.foody.common.model.request.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserRequest {
    private String username;
    private String email;
    private String password;
}
