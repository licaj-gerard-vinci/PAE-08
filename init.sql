DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

DROP TABLE IF EXISTS pae.school_years;
CREATE TABLE pae.school_years
(
    school_year_id     SERIAL PRIMARY KEY,
    year CHAR(9)
);


INSERT INTO pae.school_years VALUES (DEFAULT,'2023-2024');
INSERT INTO pae.school_years VALUES (DEFAULT,'2020-2021');




DROP TABLE IF EXISTS pae.users;
CREATE TABLE pae.users
(
    user_id                SERIAL PRIMARY KEY,
    email                  TEXT NOT NULL,
    password               TEXT NOT NULL,
    lastname               TEXT NOT NULL,
    firstname             TEXT NOT NULL,
    phone_number           TEXT NOT NULL,
    registration_date      DATE NOT NULL,
    user_role              CHAR(1) NOT NULL,
    school_year_id       INTEGER REFERENCES pae.school_years(school_year_id),
    has_internship        BOOLEAN NOT NULL
);


INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date,has_internship)VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'P', '21-09-20',false);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date,has_internship)VALUES ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'P', '21-09-20',false);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date,has_internship)VALUES ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'P', '21-09-20',false);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date,has_internship)VALUES ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'A', '21-09-20',false);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date, school_year_id,has_internship)VALUES ('Line', 'Caroline', '0486 00 00 01', 'Caroline.line@student.vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'E', '18-09-20', 1,false);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date, school_year_id,has_internship)VALUES ('Ile', 'Achille', '0487 00 00 01', 'Ach.ile@student.vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'E', '18-09-23', 1,false);
    INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date, school_year_id,has_internship)VALUES ('Ile', 'Basile', '0488 00 00 01', 'Basile.Ile@student.vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'E', '18-09-23', 1,true);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date, school_year_id,has_internship)VALUES ('skile', 'Achille', '0490 00 00 01', 'Achille.skile@student.vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'E', '18-09-23', 1,true);
INSERT INTO pae.users (lastname, firstname, phone_number, email, password, user_role, registration_date, school_year_id,has_internship)VALUES ('skile', 'Carole', '0489 00 00 01', 'Carole.skile@student.vinci.be', '$2a$10$gBeAqmeUASae1u3ak3arcuuNqmC59wV.wuhFUjrOhaWrZen6JWQU2', 'E', '18-09-23', 1,true);






DROP TABLE IF EXISTS pae.companies;
CREATE TABLE pae.companies(
                              company_id                  SERIAL PRIMARY KEY,
                              name                        TEXT NOT NULL,
                              designation                 TEXT,
                              address                     TEXT NOT NULL,
                              city                        TEXT NOT NULL,
                              phone_number                TEXT,
                              email                       TEXT,
                              is_blacklisted              BOOLEAN NOT NULL,
                              blacklist_reason            TEXT
);




INSERT INTO pae.companies (name,designation,phone_number,address,city,is_blacklisted) VALUES ('Assyst Europe','','02.609.25.00','Avenue du Japon, 1/B9','1420 Braine-l''Alleud',false);
INSERT INTO pae.companies (name,designation,phone_number,address,city,is_blacklisted) VALUES ('LetsBuild','','014 54 67 54','Chaussée de Bruxelles, 135A','1310 La Hulpe',false);
INSERT INTO pae.companies (name,designation,phone_number,address,city,is_blacklisted) VALUES ('Niboo','','0487 02 79 13','Boulevard du Souverain, 24','1170 Watermael-Boisfort',false);
INSERT INTO pae.companies (name,designation,phone_number,address,city,is_blacklisted) VALUES ('Sopra Steria','','02 566 66 66','Avenue Arnaud Fraiteur, 15/23','1050 Bruxelles',false);

DROP TABLE IF EXISTS pae.contacts;
CREATE TABLE pae.contacts(
                             contact_id              SERIAL PRIMARY KEY,
                             company_id              INTEGER REFERENCES pae.companies(company_id) NOT NULL,
                             student_id              INTEGER REFERENCES pae.users(user_id) NOT NULL,
                             school_year_id        INTEGER REFERENCES pae.school_years(school_year_id) NOT NULL,
                             contact_status          TEXT NOT NULL,
                             meeting_place           TEXT,
                             refusal_reason          TEXT
);

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,meeting_place)VALUES (1,9,2,'accepted','distenciel');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,meeting_place)VALUES (1,6,4,'accepted','on site');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,refusal_reason,meeting_place)VALUES (1,6,3,'refused','N ont pas accepté d avoir un entretien','distance');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,meeting_place)VALUES (1,7,1,'accepted','on site');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,meeting_place)VALUES (1,7,2,'on hold','distance');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status)VALUES (1,7,4,'on hold');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,refusal_reason,meeting_place)VALUES (1,7,3,'refused','ne prennent qu un seul étudiant','on site');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status,meeting_place)VALUES (1,5,3,'taken','distance');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status)VALUES (1,5,4,'initiated');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status)VALUES (1,5,2,'initiated');

INSERT INTO pae.contacts (school_year_id,student_id,company_id,contact_status)VALUES (1,8,4,'initiated');



DROP TABLE IF EXISTS pae.managers;
CREATE TABLE pae.managers
(
    manager_id          SERIAL PRIMARY KEY,
    lastname           TEXT NOT NULL,
    firstname          TEXT NOT NULL,
    phone_number        TEXT,
    email               TEXT,
    company_id          INTEGER REFERENCES pae.companies(company_id)
);



INSERT INTO pae.managers (lastname,firstname,phone_number,email,company_id)VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com', 2);
INSERT INTO pae.managers (lastname,firstname,phone_number,company_id)VALUES ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', 4);
INSERT INTO pae.managers (lastname,firstname,phone_number,email,company_id)VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1);




DROP TABLE IF EXISTS pae.internships;
CREATE TABLE pae.internships
(
    internship_id           SERIAL PRIMARY KEY,
    manager_id              INTEGER REFERENCES pae.managers(manager_id),
    student_id              INTEGER REFERENCES pae.users(user_id),
    contact_id              INTEGER REFERENCES pae.contacts(contact_id),
    company_id              INTEGER REFERENCES pae.companies(company_id),
    school_year_id        INTEGER REFERENCES pae.school_years(school_year_id) NOT NULL,
    topic                   TEXT,
    date_of_signature       DATE
);

INSERT INTO pae.internships(manager_id, student_id, contact_id, company_id, school_year_id, topic, date_of_signature)VALUES (1, 9, 1, 2, 1, 'Un ERP : Odoo', '10-10-23');
INSERT INTO pae.internships(manager_id, student_id, contact_id, company_id, school_year_id, topic, date_of_signature)VALUES (2, 6, 2, 4, 1, 'sBMS project - a complex environment', '23-11-23');
INSERT INTO pae.internships(manager_id, student_id, contact_id, company_id, school_year_id, topic, date_of_signature)VALUES (3, 7, 4, 1, 1, 'CRM : Microsoft Dynamics 365 For Sales', '12-10-23');