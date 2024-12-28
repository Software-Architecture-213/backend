package com.example.identityservice.dto.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersInfoRequest {
    String pageToken;
    int maxResults = 5;
}
