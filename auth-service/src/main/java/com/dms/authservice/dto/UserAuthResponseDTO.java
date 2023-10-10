package com.dms.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthResponseDTO {

    private final String username;

    private final String token;

}
