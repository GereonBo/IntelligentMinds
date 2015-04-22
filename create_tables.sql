CREATE TABLE users
(
    id NUMBER PRIMARY KEY,
    name VARCHAR,
    user_name VARCHAR,
    password VARCHAR,
    email VARCHAR,
    profile_text VARCHAR
);

CREATE TABLE groups
(
    id NUMBER PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE user_groups
(
    user_id NUMBER,
    group_id NUMBER,
    CONSTRAINT pk_user_groups PRIMARY KEY (user_id, group_id)
);

CREATE TABLE user_contacts
(
    user_id NUMBER,
    contact_id NUMBER,
    CONSTRAINT pk_user_contacts PRIMARY KEY (user_id, contact_id)
);

CREATE TABLE messages
(
    id NUMBER PRIMARY KEY,
    creaton_date DATE,
    text VARCHAR,
    user_sender_id NUMBER,
    user_receiver_id NUMBER,
    group_receiver_id NUMBER
);

ALTER TABLE user_groups
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_groups
ADD CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups(id);

ALTER TABLE user_contacts
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE user_contacts
ADD CONSTRAINT fk_contact_id FOREIGN KEY (contact_id) REFERENCES users(id);

ALTER TABLE messages
ADD CONSTRAINT fk_user_sender_id FOREIGN KEY (user_sender_id) REFERENCES users(id);

ALTER TABLE messages
ADD CONSTRAINT fk_user_receiver_id FOREIGN KEY (user_receiver_id) REFERENCES users(id);

ALTER TABLE messages
ADD CONSTRAINT fk_group_receiver_id FOREIGN KEY (group_receiver_id) REFERENCES groups(id);