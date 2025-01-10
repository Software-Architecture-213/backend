package com.example.identityservice.controller;

import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.request.user.DisableUserRequest;
import com.example.identityservice.dto.request.user.UsersInfoRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.dto.response.user.UsersInfoResponse;
import com.example.identityservice.enums.Role;
import com.example.identityservice.service.UserService;
import org.springframework.http.HttpStatus;
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
        UsersInfoResponse usersInfo = userService.getUsersInfo(usersInfoRequest);
        final var notAdminUsers = usersInfo.getData().stream().filter(u -> u.getRole() != Role.ADMIN).toList();
        usersInfo.setData(notAdminUsers);
        return ResponseEntity.ok(usersInfo);
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoResponse> getMyProfile(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserInfo(userId, null));
    }

    @PutMapping("/me")
    public ResponseEntity<UserInfoResponse> updateMe(Authentication authentication,
            @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateByUserId(userId, userUpdateRequest));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<UserInfoResponse> updateUserById(@PathVariable String id, @Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateByUserId(id, userUpdateRequest));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{email}/disable")
    public ResponseEntity<UserInfoResponse> enableUser(@PathVariable String email, @RequestBody DisableUserRequest request) {
        request.setEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userService.disableUser(request));
    }

    @PostMapping("/me/upload-image")
    public ResponseEntity<UserInfoResponse> updatePhoto(Authentication authentication,
            @RequestParam("file") MultipartFile file) {
        String userId = (String) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePhoto(userId, file));
    }

}
