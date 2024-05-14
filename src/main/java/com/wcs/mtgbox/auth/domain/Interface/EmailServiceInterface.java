package com.wcs.mtgbox.auth.domain.Interface;
import com.mailjet.client.errors.MailjetException;

public interface EmailServiceInterface {
    void SendMail(String toEmail, String body) throws MailjetException;
}
