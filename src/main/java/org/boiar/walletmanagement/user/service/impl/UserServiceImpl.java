package org.boiar.walletmanagement.user.service.impl;

import org.boiar.walletmanagement.user.entity.User;
import org.boiar.walletmanagement.user.exception.UserErrorCode;
import org.boiar.walletmanagement.user.exception.UserException;
import org.boiar.walletmanagement.user.mapper.UserMapper;
import org.boiar.walletmanagement.user.repository.UserRepository;
import org.boiar.walletmanagement.user.response.UserProfileResponse;
import org.boiar.walletmanagement.user.service.UserService;
import org.boiar.walletmanagement.user.request.ChangePasswordRequest;
import org.boiar.walletmanagement.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    public UserDetails loadUserByEmail(String email) {
        return loadUserByUsername(email);
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return this.userRepo.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found !"));
    }


    @Override
    public void updateProfileInfo(ProfileUpdateRequest req, UUID userId) {
        User userObj = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        this.userMapper.mergeUserInfo(userObj, req);
        this.userRepo.save(userObj);
    }

    @Override
    public void changePassword(ChangePasswordRequest req, UUID userId) {
        if (!req.getNewPassword()
                .equals(req.getConfirmPassword())) {
            throw new UserException(UserErrorCode.CHANGE_PASSWORD_MISMATCH);
        }

        final User savedUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));


        if (!this.passwordEncoder.matches(req.getCurrentPassword(),
                savedUser.getPassword())) {
            throw new UserException(UserErrorCode.INVALID_CURRENT_PASSWORD);
        }

        final String encoded = this.passwordEncoder.encode(req.getNewPassword());
        savedUser.setPassword(encoded);
        this.userRepo.save(savedUser);
    }

    @Override
    public void deactivateAccount(UUID userId) {
        final User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!user.isEnabled()) {
            throw new UserException(UserErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }

        user.setEnabled(false);
        this.userRepo.save(user);
    }

    @Override
    public void reactivateAccount(UUID userId) {
        final User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (user.isEnabled()){
            throw new UserException(UserErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }

        user.setEnabled(true);
        this.userRepo.save(user);
    }

    @Override
    public void deleteAccount(UUID userId) {
        // this method need the rest of the entities
        // the logic is just to schedule a profile for deletion
        // and then a scheduled job will pick up the profiles and delete everything
    }

    @Override
    public UserProfileResponse getUserById(UUID userId) {
        return this.userRepo.findById(userId)
                          .map(this.userMapper::toUserProfileResponse)
                                .orElseThrow(() ->
                                        new UserException(UserErrorCode.USER_NOT_FOUND));
    }


}
