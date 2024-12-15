package com.example.identityservice.dto.request.auth;

import com.example.identityservice.enums.Gender;
import com.example.identityservice.enums.Role;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationRequest {
    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be of valid format")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, message = "Password length must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Display name must not be empty")
    @Size(max = 50, message = "Display name length must not exceed 50 characters")
    private String displayName;

    @NotNull(message = "Role must not be null")
    private Role role;


    @Pattern(
            regexp = "^\\+[1-9][0-9]{1,11}$",
            message = "Phone number must be E.164 compliant (e.g., +84123456789)"
    )
    @NotNull(message = "Phone number must not be null")
    private String phoneNumber;

    @NotNull(message = "Date of birth must not be null")
    private Date dateOfBirth;
    @NotNull(message = "Gender must be MALE or FEMALE")
    private Gender gender;
}
