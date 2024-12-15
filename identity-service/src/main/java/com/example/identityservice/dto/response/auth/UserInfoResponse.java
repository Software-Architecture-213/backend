package com.example.identityservice.dto.response.auth;

import com.example.identityservice.enums.Gender;
import com.example.identityservice.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Builder
public class UserInfoResponse {
    private String userId;
    private String email;
    private Date dateOfBirth;
    private Gender gender;
    private String displayName;
    private boolean emailVerified;
    private String phoneNumber;
    private String photoUrl;
    private Role role;
    private boolean isDisabled;

    /* Override to format date */
    public String getDateOfBirth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateOfBirth);
    }
}
