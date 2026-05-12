package com.upc.purevibeb.security.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class PasswordForgotRequest {
    @Email
    private String email;
}