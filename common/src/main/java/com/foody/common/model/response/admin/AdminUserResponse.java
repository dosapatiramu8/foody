package com.foody.common.model.response.admin;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserResponse {
    private Long id;
    private String username;
    private String email;
}
