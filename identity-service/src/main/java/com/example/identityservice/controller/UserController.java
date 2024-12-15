package com.example.identityservice.controller;

import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final  UserService userService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfoResponse> getUserInfo(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "email", required = false) String email) {
        if (userId == null && email == null) {
            return ResponseEntity.badRequest().build();
        }
        UserInfoResponse userInfo = userService.getUserInfo(userId, email);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserInfo(userId, null));
    }


    @PutMapping("/update")
    public ResponseEntity<UserInfoResponse> updateUserByEmail(@RequestParam("email") String email, @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateByEmail(email, userUpdateRequest));
    }

}
