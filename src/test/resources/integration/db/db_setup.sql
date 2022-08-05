CREATE TABLE users
(
    id    BIGINT      NOT NULL,
    email VARCHAR(50) NOT NULL,
    name  VARCHAR(50) NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT email_uq UNIQUE (email)
);
CREATE SEQUENCE users_seq START WITH 1;

CREATE TABLE partnership_programs
(
    id          BIGINT      NOT NULL,
    title       VARCHAR(50) NOT NULL,
    description VARCHAR(200),

    CONSTRAINT partnership_programs_pk PRIMARY KEY (id),
    CONSTRAINT title_uq UNIQUE (title)
);
CREATE SEQUENCE partnership_programs_seq START WITH 1;

CREATE TABLE subscriptions
(
    user_id    BIGINT NOT NULL,
    program_id BIGINT NOT NULL,

    CONSTRAINT subscriptions_pk PRIMARY KEY (user_id, program_id)
);

ALTER TABLE subscriptions
    ADD FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE subscriptions
    ADD FOREIGN KEY (program_id) REFERENCES partnership_programs (id);