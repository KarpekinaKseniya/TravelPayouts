INSERT INTO users (id, name, email)
VALUES (1, 'Yisroel Mcarthur', 'loscar@yahoo.ca'),
       (2, 'Nabeela Leech', 'juliano@msn.com');
ALTER SEQUENCE users_id_seq RESTART WITH 3;

INSERT INTO partnership_programs (id, title, description)
VALUES (1, 'RentalCars', 'Earn commissions on car rentals worldwide'),
       (2, 'Omio', 'Earn with the Omio affiliate program and help your customers organize their European trips'),
       (3, 'Go City', 'Earn money on multi-attraction passes for tourists');
ALTER SEQUENCE partnership_programs_id_seq RESTART WITH 4;

INSERT INTO subscriptions (user_id, program_id, subscribe_status)
VALUES (1, 1, 'UNSUBSCRIBED'),
       (1, 3, 'SUBSCRIBED'),
       (2, 1, 'BLOCK'),
       (2, 2, 'SUBSCRIBED'),
       (2, 3, 'SUBSCRIBED');