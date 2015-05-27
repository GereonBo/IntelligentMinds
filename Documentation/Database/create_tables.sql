CREATE TABLE users
(
id INTEGER PRIMARY KEY,
name VARCHAR(45),
user_name VARCHAR(45),
password CHAR(56),
email VARCHAR(45),
profile_text VARCHAR(256)
);
CREATE TABLE groups
(
id INTEGER PRIMARY KEY,
name VARCHAR(45)
);
CREATE TABLE user_groups
(
user_id INTEGER,
group_id INTEGER,
CONSTRAINT pk_user_groups PRIMARY KEY (user_id, group_id)
);
CREATE TABLE user_contacts
(
user_id INTEGER,
contact_id INTEGER,
CONSTRAINT pk_user_contacts PRIMARY KEY (user_id, contact_id)
);
CREATE TABLE messages
(
id INTEGER PRIMARY KEY,
creaton_date DATE,
text VARCHAR(300),
user_sender_id INTEGER,
user_receiver_id INTEGER,
group_receiver_id INTEGER
);
ALTER TABLE user_groups
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_groups
ADD CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES groups(id);
ALTER TABLE user_contacts
ADD CONSTRAINT fk_contacts_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE user_contacts
ADD CONSTRAINT fk_contacts_contact_id FOREIGN KEY (contact_id) REFERENCES users(id);
ALTER TABLE messages
ADD CONSTRAINT fk_messages_user_sender_id FOREIGN KEY (user_sender_id) REFERENCES users(id);
ALTER TABLE messages
ADD CONSTRAINT fk_messages_user_receiver_id FOREIGN KEY (user_receiver_id) REFERENCES users(id);
ALTER TABLE messages
ADD CONSTRAINT fk_messages_group_receiver_id FOREIGN KEY (group_receiver_id) REFERENCES groups(id);
