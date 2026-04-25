package org.boiar.walletmanagement.user.mapper;

import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {


    public void mergeUserInfo(final User user, final ProfileUpdateRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
    }

    public UserProfileResponse toUserProfileResponse(final User user) {
        return UserProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
