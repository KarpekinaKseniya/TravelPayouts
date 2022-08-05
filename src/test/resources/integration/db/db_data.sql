ALTER SEQUENCE users_seq RESTART WITH 1;
INSERT INTO users (id, name, email)
VALUES (1, 'Yisroel Mcarthur', 'loscar@yahoo.ca'),
       (2, 'Nabeela Leech', 'juliano@msn.com');
ALTER SEQUENCE users_seq RESTART WITH 3;

ALTER SEQUENCE partnership_programs_seq RESTART WITH 1;
INSERT INTO partnership_programs (id, title, description)
VALUES (1, 'RentalCars', 'Earn commissions on car rentals worldwide'),
       (2, 'Omio', 'Earn with the Omio affiliate program and help your customers organize their European trips'),
       (3, 'Go City', 'Earn money on multi-attraction passes for tourists');
ALTER SEQUENCE partnership_programs_seq RESTART WITH 4;

INSERT INTO subscriptions (user_id, program_id)
VALUES (1, 1),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 3);