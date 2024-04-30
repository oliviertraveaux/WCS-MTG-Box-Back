package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.mailjet.client.errors.MailjetException;
import com.wcs.mtgbox.auth.domain.entity.PasswordToken;
import com.wcs.mtgbox.auth.domain.entity.User;
import com.wcs.mtgbox.auth.domain.service.EmailServiceInterface;
import com.wcs.mtgbox.auth.domain.service.PasswordForgottenServiceInterface;
import com.wcs.mtgbox.auth.domain.service.auth.UserRegistrationService;
import com.wcs.mtgbox.auth.infrastructure.exception.registration.PasswordNotSimilarException;
import com.wcs.mtgbox.auth.infrastructure.exception.registration.PasswordTokenException;
import com.wcs.mtgbox.auth.infrastructure.repository.PasswordTokenRepository;
import com.wcs.mtgbox.auth.infrastructure.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;

@Service
public class PasswordForgottenService implements PasswordForgottenServiceInterface {
    private static UserRepository userRepository;
    private static PasswordTokenRepository passwordTokenRepository;

    private static EmailServiceInterface emailService;

    private static UserRegistrationService userRegistrationService;

    public PasswordForgottenService(UserRepository userRepository, PasswordTokenRepository passwordTokenRepository, EmailServiceInterface emailService, UserRegistrationService userRegistrationService) {
        PasswordForgottenService.userRepository = userRepository;
        PasswordForgottenService.passwordTokenRepository = passwordTokenRepository;
        PasswordForgottenService.emailService = emailService;
        PasswordForgottenService.userRegistrationService = userRegistrationService;
    }

    @Override
    public  String tokenGenerator(String email) throws MailjetException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        LocalDateTime dateTime = LocalDateTime.now();
        String token = tokenProvider();
        PasswordToken passwordToken = new PasswordToken();
        passwordToken.setToken(token);
        passwordToken.setUser(user);
        passwordToken.setDateOfExpiration(dateTime.plusHours(24));

        passwordTokenRepository.save(passwordToken);
        userRepository.save(user);

        mailSender(user, token);
        return token;
    }

    @Override
    public void mailSender(User user, String token) throws MailjetException {
        emailService.SendMail(user.getEmail(), "Pour créer un nouveau mot de passe, veuillez accéder à cette adresse http://localhost:4200/forgotten-password/" + token);
    }

    @Override
    public  String tokenProvider() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[100 * 3/4];
        random.nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Override
    public  void checkTokenValidityAndCreateNewPassword(String token, User user) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);

        if (passwordToken == null || LocalDateTime.now().isAfter(passwordToken.getDateOfExpiration()) || passwordToken.isChanged()) {
            throw new PasswordTokenException();
        }

        User userToPersist = passwordToken.getUser();

        if (!Objects.equals(user.getPlainPassword(), user.getPlainPasswordVerification())) {
            throw new PasswordNotSimilarException();
        }

        String newHashedPasswordUser = userRegistrationService.GenerateHashedPassword(user.getPlainPassword());
        userToPersist.setPassword(newHashedPasswordUser);
        passwordToken.setChanged(true);
        passwordTokenRepository.save(passwordToken);
        userRepository.save(userToPersist);

    }
}
