INSERT INTO role (type) VALUES ('ADMIN');

INSERT INTO "user" (user_name, email, password, department, city, role_id, creation_date, last_connection_date,is_active, is_banned)
VALUES ('john','john@doe.com', '$2a$12$S/VXri58yQkBCEIk9CnRtOSXZLFEa03dd5gJ5YwfqFH8wR6Lbfq8S', 31000, 'Toulouse', 1, '2024-01-15 10:14:38.597851', '2024-01-15 10:14:38.597851',1, 0);
