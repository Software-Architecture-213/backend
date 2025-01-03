package com.example.identityservice.dto.response.auth;

import com.example.identityservice.enums.Gender;
import com.example.identityservice.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;


@Data
@Builder
public class UserInfoResponse {
    private String userId;
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL) // Excludes null fields
    private Date dateOfBirth;
    private Gender gender;

    private String displayName;
    private boolean emailVerified;
    private String phoneNumber;
    private String photoUrl;
    private Role role;
    private boolean isDisabled;
    private Date lastSignIn;

    /* Override to format date */
    public String getDateOfBirth() {
        if (dateOfBirth == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfBirth);
    }

    public String getLastSignIn() {
        if (lastSignIn == null || lastSignIn.toString().contains("1970")) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(lastSignIn);
    }
}
