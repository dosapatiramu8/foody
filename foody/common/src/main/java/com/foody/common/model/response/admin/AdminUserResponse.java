package com.foody.common.model.response.admin;

import com.foody.common.model.response.misc.UserResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AdminUserResponse extends UserResponse {
    private String role;
}
