package fr.eni.baralivre.back.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private String lastName;

    private String firstName;
}