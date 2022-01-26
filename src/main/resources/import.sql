INSERT INTO user(id, userName, password, loggedIn, role) VALUES (nextval('hibernate_sequence'), 'user', 'pass', false, 'USER');
INSERT INTO user(id, userName, password, loggedIn, role) VALUES (nextval('hibernate_sequence'), 'admin', 'pass', false, 'ADMIN');
