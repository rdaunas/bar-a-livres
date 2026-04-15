package fr.eni.baralivre.back.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

}
