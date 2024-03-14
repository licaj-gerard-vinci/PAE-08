DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

DROP TABLE IF EXISTS pae.academic_years;
CREATE TABLE pae.academic_years
(
    academic_year_id     SERIAL PRIMARY KEY,
    year CHAR(9)
);


INSERT INTO pae.academic_years VALUES (1,'2023-2024');


DROP TABLE IF EXISTS pae.users;
CREATE TABLE pae.users
(
    user_id                SERIAL PRIMARY KEY,
    email                  TEXT NOT NULL,
    password               TEXT NOT NULL,
    last_name              TEXT NOT NULL,
    first_name             TEXT NOT NULL,
    phone_number           TEXT NOT NULL,
    registration_date      DATE NOT NULL,
    user_role              CHAR(1) NOT NULL,
    academic_year_id       INTEGER REFERENCES pae.academic_years(academic_year_id)
);

INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, user_role) VALUES ('admin@vinci.be','$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2','admin last name','admin first name','0489055536',date(now()),'A');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, user_role, academic_year_id) VALUES ('resul.ramadani@student.vinci.be','$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2','Ramadani','Resul','0489055536',date(now()),'E',1);
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, user_role) VALUES ('prof@vinci.be','$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2','prof last name','prof first name','0489055536',date(now()),'P');
INSERT INTO pae.users (email, password, last_name, first_name, phone_number, registration_date, user_role, academic_year_id) VALUES ('etude@student.vinci.be','$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2','Student Last Name','Student First Name','0489055536',date(now()),'E',1);






DROP TABLE IF EXISTS pae.companies;
CREATE TABLE pae.companies(
                              company_id                  SERIAL PRIMARY KEY,
                              name                        TEXT NOT NULL,
                              designation                 TEXT,
                              address                     TEXT NOT NULL,
                              phone_number                TEXT,
                              email                       TEXT,
                              is_blacklisted              BOOLEAN NOT NULL,
                              blacklist_reason            TEXT
);

INSERT INTO pae.companies (name, designation, address, phone_number, email, is_blacklisted) VALUES ('Rockstar', 'RO', 'Los Angeles', '0489055536', 'rockstar@gta.com', false);


DROP TABLE IF EXISTS pae.contacts;
CREATE TABLE pae.contacts(
                             contact_id              SERIAL PRIMARY KEY,
                             company_id              INTEGER REFERENCES pae.companies(company_id) NOT NULL,
                             student_id              INTEGER REFERENCES pae.users(user_id) NOT NULL,
                             academic_year_id        INTEGER REFERENCES pae.academic_years(academic_year_id) NOT NULL,
                             contact_status          TEXT NOT NULL,
                             meeting_place           TEXT,
                             refusal_reason          TEXT
);

INSERT INTO pae.contacts(company_id, student_id, academic_year_id, contact_status, meeting_place) VALUES (1, 2, 1, 'accepted', 'remote');
INSERT INTO pae.contacts(company_id, student_id, academic_year_id, contact_status, meeting_place, refusal_reason) VALUES (1, 2, 1, 'refused', 'remote', 'Unattractive appearance');
INSERT INTO pae.contacts(company_id, student_id, academic_year_id, contact_status, meeting_place) VALUES (1, 2, 1, 'On hold', 'on-site');
INSERT INTO pae.contacts(company_id, student_id, academic_year_id, contact_status, meeting_place, refusal_reason) VALUES (1, 2, 1, 'refused', 'remote', 'Unattractive appearance');


DROP TABLE IF EXISTS pae.managers;
CREATE TABLE pae.managers
(
    manager_id          SERIAL PRIMARY KEY,
    last_name           TEXT NOT NULL,
    first_name          TEXT NOT NULL,
    phone_number        TEXT,
    email               TEXT,
    company_id          INTEGER REFERENCES pae.companies(company_id)
);

INSERT INTO pae.managers (last_name, first_name, phone_number, email, company_id) VALUES ('responsable', 'Resul', '0489055536', 'responsable@gmail.com', 1);




DROP TABLE IF EXISTS pae.internships;
CREATE TABLE pae.internships
(
    internship_id           SERIAL PRIMARY KEY,
    manager_id              INTEGER REFERENCES pae.managers(manager_id),
    student_id              INTEGER REFERENCES pae.users(user_id),
    contact_id              INTEGER REFERENCES pae.contacts(contact_id),
    company_id              INTEGER REFERENCES pae.companies(company_id),
    academic_year_id        INTEGER REFERENCES pae.academic_years(academic_year_id) NOT NULL,
    topic                   TEXT,
    date_of_signature       DATE
);

INSERT INTO pae.internships (manager_id, student_id, contact_id, company_id, academic_year_id, topic, date_of_signature) VALUES (1, 2, 1, 1, 1, 'Sujet de mon stage : PHP', '2004-04-15');
INSERT INTO pae.internships (manager_id, student_id, contact_id, company_id, academic_year_id, date_of_signature) VALUES (1, 4, 1, 1, 1, '2004-04-15');
