package fr.eni.baralivre.back.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}
