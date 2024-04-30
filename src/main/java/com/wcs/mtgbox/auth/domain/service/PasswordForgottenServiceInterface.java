package com.wcs.mtgbox.auth.domain.service;


import com.mailjet.client.errors.MailjetException;
import com.wcs.mtgbox.auth.domain.entity.User;

public interface PasswordForgottenServiceInterface {
   String tokenGenerator(String email) throws MailjetException;
     String tokenProvider();

     void mailSender(User user, String token) throws MailjetException;

     void checkTokenValidityAndCreateNewPassword(String token, User user);
}
