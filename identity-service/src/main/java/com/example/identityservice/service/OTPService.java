package com.example.identityservice.service;

import com.example.identityservice.client.FirebaseUserClient;
import com.example.identityservice.dto.request.auth.UserUpdateRequest;
import com.example.identityservice.dto.response.auth.UserInfoResponse;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.utility.OTPUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPService {
    private final MailService mailService;
    private final FirebaseUserClient firebaseUserClient;
    private final Map<String, OTPData> otpStorage = new HashMap<>();

    public String generateAndSendOTP(String email) {
        String otp = OTPUtil.generateOTP();
        UserInfoResponse userInfoResponse = firebaseUserClient.getUserInfo(null, email);
        if (userInfoResponse == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        otpStorage.put(email, new OTPData(otp, LocalDateTime.now().plusMinutes(5))); // Expiry 5 minutes
        mailService.sendMail(email, "Your OTP Code", "Your One-Time Password (OTP) is: " + otp + "\n\nThis OTP is valid for 5 minutes.");
        return otp;
    }

    public boolean validateOTP(String email, String otp) {
        OTPData otpData = otpStorage.get(email);

        if (otpData != null && otpData.otp().equals(otp) && otpData.expiryTime().isAfter(LocalDateTime.now())) {
            UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder().disabled(false).build();
            firebaseUserClient.updateByEmail(email,userUpdateRequest);
            otpStorage.remove(email); // OTP can be used only once
            return true;
        }
        return false;
    }

        private record OTPData(String otp, LocalDateTime expiryTime) {
    }
}
