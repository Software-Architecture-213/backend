package com.example.identityservice.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnableUserRequest {
        
    @JsonIgnore
    private String userId;

    private boolean enable;
}
