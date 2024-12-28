package com.example.identityservice.controller;

import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.request.user.UsersInfoRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.dto.response.user.UsersInfoResponse;
import com.example.identityservice.enums.Role;
import com.example.identityservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<UserInfoResponse> getUserInfo(
    // @RequestParam(value = "userId", required = false) String userId,
    // @RequestParam(value = "email", required = false) String email) {
    // if (userId == null && email == null) {
    // return ResponseEntity.badRequest().build();
    // }
    // UserInfoResponse userInfo = userService.getUserInfo(userId, email);
    // return ResponseEntity.ok(userInfo);
    // }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<UsersInfoResponse> getUsersInfo(Authentication authentication, @ModelAttribute UsersInfoRequest usersInfoRequest) {
        UsersInfoResponse userInfo = userService.getUsersInfo(usersInfoRequest);
        final var notAdminUsers = userInfo.getData().stream().filter(u -> u.getRole() != Role.ADMIN).toList();
        userInfo.setData(notAdminUsers);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserInfo(userId, null));
    }

    @PutMapping("")
    public ResponseEntity<UserInfoResponse> updateUserByEmail(Authentication authentication,
            @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateByUserId(userId, userUpdateRequest));
    }

    @PostMapping("/upload-image")
    public ResponseEntity<UserInfoResponse> updatePhoto(Authentication authentication,
            @RequestParam("file") MultipartFile file) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePhoto(userId, file));
    }

}
