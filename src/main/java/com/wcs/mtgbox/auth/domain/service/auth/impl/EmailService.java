package com.wcs.mtgbox.auth.domain.service.auth.impl;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import com.wcs.mtgbox.auth.domain.service.EmailServiceInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceInterface {
    @Value("${mailjet.apiKey}")
    public String apiKey;

    @Value("${mailjet.apiSecret}")
    public String apiSecret;

    @Override
    public void SendMail(String toEmail, String body) throws MailjetException {
        ClientOptions options = ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(apiSecret)
                .build();

        MailjetClient client = new MailjetClient(options);

        String htmlContent = "<div style='font-family: Arial, sans-serif; text-align: center; color: #333;'>" +
                "<img src='https://boutique.magiccorporation.com/scan.php?num=519' alt='Magic Card Image' style='width: 250px; height: 350px; margin-bottom: 20px;'>" +
                "<h1 style='color: #5a5a5a;'>Mot de passe oublié?</h1>" +
                "<p>Bonjour, il semble que vous ayez demandé à réinitialiser votre mot de passe.</p>" +
                "<p style='padding: 15px; background-color: #f8f8f8; border: 1px solid #eaeaea;'>" + body + "</p>" +
                "</div>";

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(toEmail, "stanislav"))
                .from(new SendContact("antoine@taleez.com", "MTG Box mot de passe oublié"))
                .htmlPart(htmlContent)
                .subject("Mot de passe oublié")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        SendEmailsResponse response = request.sendWith(client);
    }
}
