package com.example.identityservice.service;

import com.example.identityservice.client.FirebaseUserClient;
import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.request.user.DisableUserRequest;
import com.example.identityservice.dto.request.user.UsersInfoRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.dto.response.user.UsersInfoResponse;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.utility.CloudinaryUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MailService mailService;
    private final CloudinaryService cloudinaryService;
    private final FirebaseUserClient firebaseUserClient;

    public UserInfoResponse getUserInfo(String userId, String email) {
        return firebaseUserClient.getUserInfo(userId, email);
    }

    public UsersInfoResponse getUsersInfo(UsersInfoRequest usersInfoRequest) {
        return firebaseUserClient.getUsers(usersInfoRequest.getPageToken(), usersInfoRequest.getMaxResults());
    }

    public UserInfoResponse updateByEmail(@NonNull String email, @NonNull UserUpdateRequest userUpdateRequest) {
        return firebaseUserClient.updateByEmail(email, userUpdateRequest);
    }

    public UserInfoResponse updateByUserId(@NonNull String userId, @NonNull UserUpdateRequest userUpdateRequest) {
        return firebaseUserClient.updateByUid(userId, userUpdateRequest);
    }

    public UserInfoResponse updatePhoto(@NonNull String userId, MultipartFile file) {
        final UserInfoResponse user = firebaseUserClient.getUserInfo(userId, null);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        String oldPhotoUrl = user.getPhotoUrl();
        String oldPublicId = CloudinaryUtil.extractPublicIdFromUrl(oldPhotoUrl);
        if (oldPublicId != null) {
            cloudinaryService.deleteImage(oldPublicId);
        }
        String newPhotoUrl = cloudinaryService.uploadImage(file);
        if (newPhotoUrl == null) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        UserUpdateRequest updateRequest = UserUpdateRequest.builder().photoUrl(newPhotoUrl).build();
        return firebaseUserClient.updateByUid(userId, updateRequest);
    }

    public UserInfoResponse disableUser(DisableUserRequest userRequest) {
        UserInfoResponse userInfoResponse = firebaseUserClient.disableUser(userRequest.getEmail(),
                userRequest.isDisabled());
        String subject = !userRequest.isDisabled() ? "Your account has been activated!"
                : "Your account has been deactivated!";
        String message = userRequest.getMessage();
        if (userRequest.getMessage() == null || message.isBlank()) {
            message = !userRequest.isDisabled()
                    ? "Dear " + userRequest.getEmail()
                            + ", we are pleased to inform you that your account has been successfully activated. We look forward to having you use our app. If you have any questions or need assistance, feel free to reach out to our support team."
                    : "Dear " + userRequest.getEmail()
                            + ", we would like to inform you that your account has been deactivated due to a violation of our policies. If you believe this action was taken in error, please contact our support team for further assistance. We value your experience and hope to resolve any misunderstandings.";
        }
        mailService.sendMail(userRequest.getEmail(), subject, message);
        return userInfoResponse;
    }

    public UserInfoResponse getUserInfoByEmail(@NonNull String email) {
        return firebaseUserClient.getUserInfo(null, email);
    }
}
