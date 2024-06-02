package com.foody.common.model.request.admin;

import com.foody.common.model.request.misc.UserRequest;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AdminUserRequest extends UserRequest {
    private String role;
}
