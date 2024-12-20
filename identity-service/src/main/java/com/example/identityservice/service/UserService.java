package com.example.identityservice.service;
import com.example.identityservice.client.FirebaseUserClient;
import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
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
    private final CloudinaryService cloudinaryService;
    private final FirebaseUserClient firebaseUserClient;

    public UserInfoResponse getUserInfo(String userId, String email) {
       return firebaseUserClient.getUserInfo(userId, email);
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
}
