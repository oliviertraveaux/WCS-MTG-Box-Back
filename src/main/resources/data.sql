INSERT INTO card_quality (name, abbreviation) VALUES
                                                  ('Mint', 'M'),
                                                  ('Near Mint', 'NM'),
                                                  ('Excellent', 'EX'),
                                                  ('Good', 'GD'),
                                                  ('Light Played', 'LP'),
                                                  ('Played', 'PL'),
                                                  ('Poor', 'P');
INSERT INTO card_rarity (name) VALUES
                                   ('Common'),
                                   ('Uncommon'),
                                   ('Rare'),
                                   ('Mythic Rare'),
                                   ('Basic Land'),
                                   ('Special');
INSERT INTO card_language (name) VALUES
                                     ('English'),
                                     ('French');
INSERT INTO role (type) VALUES
                            ('USER'),
                            ('ADMIN');
INSERT INTO `user` (user_name, email, password, is_active, is_banned, post_code, city, last_connection_date, creation_date, role_id)
VALUES
    ('john_doe', 'john@example.com', 'password123', true, false, 12345, 'New York', NOW(), NOW(), 1),
    ('jane_doe', 'jane@example.com', 'password456', true, false, 54321, 'Los Angeles', NOW(), NOW(), 2);
INSERT INTO card (api_card_id, name, image_url, french_name, french_image_url, mana_cost, rarity, set_abbreviation, set_name, text, artist)
VALUES
    ('12345', 'Card 1', 'image1.jpg', 'Carte 1', 'http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=395425&type=card', 3, 'Rare', 'ABC', 'Set 1', 'Texte de la carte 1', 'Artiste 1'),
    ('67890', 'Card 2', 'image2.jpg', 'Carte 2', 'http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=548186&type=card', 2, 'Common', 'DEF', 'Set 2', 'Texte de la carte 2', 'Artiste 2');
INSERT INTO user_card (user_id, card_id, quality_id, language_id, is_active)
VALUES
    (1, 1, 1, 1, true),
    (2, 2, 2, 2, false);

