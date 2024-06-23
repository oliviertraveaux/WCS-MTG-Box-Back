package com.wcs.mtgbox.transaction.offer.domain.service.impl;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import com.wcs.mtgbox.collection.domain.entity.UserCard;
import com.wcs.mtgbox.transaction.offer.domain.entity.Offer;
import com.wcs.mtgbox.transaction.offer.domain.service.OfferEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OfferEmailServiceImpl implements OfferEmailService {
    @Value("${mailjet.apiKey}")
    public String apiKey;

    @Value("${mailjet.apiSecret}")
    public String apiSecret;

    @Value(" ${mailjet.userEmail}")
    public String mailJetUserEmail;

    @Override
    public void sendOfferEmail(Offer offer) throws MailjetException {

            ClientOptions options = ClientOptions.builder()
                    .apiKey(apiKey)
                    .apiSecretKey(apiSecret)
                    .build();

            MailjetClient client = new MailjetClient(options);

            String htmlContent = buildHtmlContent(offer);

            TransactionalEmail message1 = TransactionalEmail
                    .builder()
                    .to(new SendContact(offer.getWantedUserCard().getUser().getEmail(), offer.getWantedUserCard().getUser().getUsername()))
                    .to(new SendContact(offer.getUser().getEmail(), offer.getUser().getUsername()))
                    .from(new SendContact(mailJetUserEmail, "MTG Box - Offre validée"))
                    .htmlPart(htmlContent)
                    .subject("Offre #" + offer.getId() + " validée")
                    .build();

            SendEmailsRequest request = SendEmailsRequest
                    .builder()
                    .message(message1) // you can add up to 50 messages per request
                    .build();

            // act
            SendEmailsResponse response = request.sendWith(client);
    }

    private String buildHtmlContent(Offer offer) {
        String htmlContent = "<div style='font-family: Arial, sans-serif;'>" +
                "<p>Bonjour, la transaction #" + offer.getId() + " a été validée.</p>" +
                "<p>Vous pouvez maintenant prendre contact par email entre utilisateurs afin de procéder à l'échange des cartes.</p>" +
                "<p>Voici le récapitulatif de la transaction.</p>" +
                "<p>L'utilisateur <strong>" + offer.getWantedUserCard().getUser().getUsername() + " ("+offer.getWantedUserCard().getUser().getEmail()+")</strong> propose à l'échange la carte suivante: </p>" +
                "<img src='" + getLocalizedPicture(offer.getWantedUserCard())  + "' alt='Magic Card Image' style='width: 250px; height: 350px;'>" +
                "<p><strong>" + getLocalizedName(offer.getWantedUserCard())  + "</strong></p>" +
                "<p>Edition: " + offer.getWantedUserCard().getCard().getSetName() + "</p>" +
                "<p>Qualité: " + offer.getWantedUserCard().getCardQuality().getName() + "</p>" +
                "<p>Langue: " + offer.getWantedUserCard().getCardLanguage().getName() + "</p>" +
                "<br>" +
                "<p>L'utilisateur <strong>" + offer.getUser().getUsername() + " ("+offer.getUser().getEmail()+")</strong> propose à l'échange la ou les carte(s) suivante(s): </p>" +
                "<div>" + getOfferCards(offer) + "</div>" +
                "<br>" +
                "<p>Nous vous souhaitons un bon échange de cartes</p>" +
                "<br>" +
                "<p>L'équipe MTG box</p>" +
                "</div>";
        return htmlContent;
    }
    private String getLocalizedName(UserCard userCard) {
        if (userCard.getCardLanguage().getName().equals("French")) {
            return userCard.getCard().getFrenchName();
        }
        return userCard.getCard().getName();
    }

    private String getLocalizedPicture(UserCard userCard) {
        if (userCard.getCardLanguage().getName().equals("French")) {
            return userCard.getCard().getFrenchImageUrl();
        }
        return userCard.getCard().getImageUrl();
    }

    private String getOfferCards(Offer offer) {
        String offerCards = offer.getUserCards().stream()
                .map(userCard ->
                        "<div>" +
                        "<img src='" + getLocalizedPicture(userCard) + "' alt='Magic Card Image' style='width: 250px; height: 350px; margin-bottom: 20px;'>" +
                        "<p><strong>" + getLocalizedName(userCard) + "</strong></p>" +
                        "<p>Edition: " + userCard.getCard().getSetName() + "</p>" +
                        "<p>Qualité: " + userCard.getCardQuality().getName() + "</p>" +
                        "<p>Langue: " + userCard.getCardLanguage().getName() + "</p>" +
                        "</div>"
                )
                .collect(Collectors.joining());

        return offerCards;
    }
}
