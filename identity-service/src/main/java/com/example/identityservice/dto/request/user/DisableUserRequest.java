package com.example.identityservice.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisableUserRequest {
        
    @JsonIgnore
    private String email;

    private String message;
    private boolean disabled;
}
