DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

DROP TABLE IF EXISTS pae.school_years;
CREATE TABLE pae.school_years
(
    school_year_id     SERIAL PRIMARY KEY,
    year CHAR(9),
    school_year_version  INTEGER
);

INSERT INTO pae.school_years VALUES (DEFAULT,'2020-2021',1);
INSERT INTO pae.school_years VALUES (DEFAULT,'2021-2022',1);
INSERT INTO pae.school_years VALUES (DEFAULT,'2022-2023',1);
INSERT INTO pae.school_years VALUES (DEFAULT,'2023-2024',1);




DROP TABLE IF EXISTS pae.users;
CREATE TABLE pae.users
(
    user_id                     SERIAL PRIMARY KEY,
    user_email                  TEXT NOT NULL,
    user_password               TEXT NOT NULL,
    user_lastname               TEXT NOT NULL,
    user_firstname              TEXT NOT NULL,
    user_phone_number           TEXT NOT NULL,
    user_registration_date      DATE NOT NULL,
    user_role                   CHAR(1) NOT NULL,
    user_school_year_id         INTEGER REFERENCES pae.school_years(school_year_id),
    user_has_internship         BOOLEAN NOT NULL,
    user_version                INTEGER NOT NULL
);

INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date,user_school_year_id,user_has_internship, user_version)VALUES ('Baroni', 'Raphaël', '0481 01 01 01', 'raphael.baroni@vinci.be', '$2a$10$IwlxK.aWODNWWvaVo69AEuLB517GjnbAEOy5zgOI9qki04PEK4Nva', 'P', '21-09-20',1,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date,user_school_year_id,user_has_internship, user_version)VALUES ('Lehmann', 'Brigitte', '0482 02 02 02', 'brigitte.lehmann@vinci.be', '$2a$10$IwlxK.aWODNWWvaVo69AEuLB517GjnbAEOy5zgOI9qki04PEK4Nva', 'P', '21-09-20',1,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date,user_school_year_id,user_has_internship, user_version)VALUES ('Leleux', 'Laurent', '0483 03 03 03', 'laurent.leleux@vinci.be', '$2a$10$IwlxK.aWODNWWvaVo69AEuLB517GjnbAEOy5zgOI9qki04PEK4Nva', 'P', '21-09-20',1,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date,user_school_year_id,user_has_internship, user_version)VALUES ('Lancaster', 'Annouck', '0484 04 04 04', 'annouck.lancaster@vinci.be', '$2a$10$qi4MIRvyyYAhBf/YszvlKe23I.5Ni7uu8ZOZxH8HfbcaYf1gqlicK', 'A', '21-09-20',1,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Skile', 'Elle', '0491 00 00 01', 'elle.skile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Ilotie', 'Basile', '0491 00 00 11', 'basile.ilotie@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Frilot', 'Basile', '0491 00 00 21', 'basile.frilot@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Ilot', 'Basile', '0492 00 00 01', 'basile.ilot@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Dito', 'Arnaud', '0493 00 00 01', 'arnaud.dito@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Dilo', 'Arnaud', '0494 00 00 01', 'arnaud.dilo@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Dilot', 'Cedric', '0495 00 00 01', 'cedric.dilot@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Linot', 'Auristelle', '0496 00 00 01', 'auristelle.linot@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '21-09-2021', 2,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Demoulin', 'Basile', '0496 00 00 02', 'basile.demoulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '23-09-2022', 3,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Moulin', 'Arthur', '0497 00 00 02', 'arthur.moulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '23-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Moulin', 'Hugo', '0497 00 00 03', 'hugo.moulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '23-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Demoulin', 'Jeremy', '0497 00 00 20', 'jeremy.demoulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '23-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Mile', 'Aurèle', '0497 00 00 21', 'aurele.mile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '23-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Mile', 'Frank', '0497 00 00 75', 'frank.mile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '27-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Dumoulin', 'Basile', '0497 00 00 58', 'basile.dumoulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '27-09-2022', 3,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Dumoulin', 'Axel', '0497 00 00 97', 'axel.dumoulin@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '27-09-2022', 3,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Line', 'Caroline', '0486 00 00 01', 'caroline.line@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '18-09-2023', 4,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Ile', 'Achille', '0487 00 00 01', 'ach.ile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '18-09-2023', 4,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Ile', 'Basile', '0488 00 00 01', 'basile.ile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '18-09-2023', 4,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Skile', 'Achille', '0490 00 00 01', 'achille.skile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '18-09-2023', 4,false,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Skile', 'Carole', '0489 00 00 01', 'carole.skile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '18-09-2023', 4,true,1);
INSERT INTO pae.users (user_lastname, user_firstname, user_phone_number, user_email, user_password, user_role, user_registration_date, user_school_year_id,user_has_internship, user_version)VALUES ('Ile', 'Théophile', '0488 35 33 89', 'theophile.ile@student.vinci.be', '$2a$10$flLnv7cN/XFV9WhEpr1fBuIL6o8xBHy9jd.flEdsn5KL6u0eJUKHO', 'E', '01-03-2024', 4,false,1);














DROP TABLE IF EXISTS pae.companies;
CREATE TABLE pae.companies(
                              company_id                  SERIAL PRIMARY KEY,
                              company_name                        TEXT NOT NULL,
                              company_designation                 TEXT,
                              company_address                     TEXT NOT NULL,
                              company_city                        TEXT NOT NULL,
                              company_phone_number                TEXT,
                              company_email                       TEXT,
                              company_is_blacklisted              BOOLEAN NOT NULL,
                              company_blacklist_reason            TEXT,
                              company_version                     INTEGER
);




INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('Assyst Europe','','02.609.25.00','Avenue du Japon, 1/B9','1420 Braine-l''Alleud',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('AXIS SRL','','02 752 17 60','Avenue de l''Hélianthe, 63, 135A','1180 Uccle',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('Infrabel','','02 525 22 11','Rue Bara, 135, 24','1070 Bruxelles',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('La route du papier','','02 586 16 65','Avenue des Mimosas, 83','1150 Woluwe-Saint-Pierre',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('LetsBuild','','014 54 67 54','Chaussée de Bruxelles, 135A','1310 La Hulpe',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('Niboo','','0487 02 79 13','Boulevard du Souverain, 24','1170 Watermael-Boisfort',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('Sopra Steria','','02 566 66 66','Avenue Arnaud Fraiteur, 15/23','1050 Bruxelles',false,1);
INSERT INTO pae.companies (company_name,company_designation,company_phone_number,company_address,company_city,company_is_blacklisted, company_version) VALUES ('The Bayard Partnership','','02 309 52 45','Grauwmeer, 1/57 bte 55','3001 Leuven',false,1);





DROP TABLE IF EXISTS pae.contacts;
CREATE TABLE pae.contacts(
                             contact_id              SERIAL PRIMARY KEY,
                             contact_company_id              INTEGER REFERENCES pae.companies(company_id) NOT NULL,
                             contact_student_id              INTEGER REFERENCES pae.users(user_id) NOT NULL,
                             contact_school_year_id        INTEGER REFERENCES pae.school_years(school_year_id) NOT NULL,
                             contact_status          TEXT NOT NULL,
                             contact_meeting_place           TEXT,
                             contact_refusal_reason          TEXT,
                             contact_version                            INTEGER
);

-- CONTACTS FOR 2023-2024
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (4,25,5,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (4,22,7,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (4,22,6,'refusé','N''ont pas accepté d''avoir un entretien','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (4,23,1,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (4,23,5,'suspendu','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,23,7,'suspendu',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (4,23,6,'refusé','ne prennent qu un seul étudiant','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (4,21,6,'refusé','Pas d’affinité avec le l’ERP Odoo','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,21,7,'non suivi',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version,contact_meeting_place)VALUES (4,21,5,'pris',1,'distance');
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,26,7,'initié',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,26,6,'initié',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,26,5,'initié',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (4,24,7,'initié',1);


-- CONTACTS FOR 2021-2022
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (2,5,4,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (2,8,7,'non suivi',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (2,7,8,'refusé','ne prennent pas de stage','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (2,9,7,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (2,10,7,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (2,11,1,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (2,11,7,'refusé','Choix autre étudiant','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (2,12,3,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_version)VALUES (2,12,7,'suspendu',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (2,12,6,'refusé','Choix autre étudiant','distance',1);

-- CONTACTS FOR 2022-2023
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,16,1,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,14,2,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,15,2,'accepté','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,17,2,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,18,2,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (3,19,2,'refusé','Entretien n''a pas eu lieu','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (3,19,6,'refusé','Entretien n''a pas eu lieu','sur place',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (3,19,7,'refusé','Entretien n''a pas eu lieu','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_meeting_place,contact_version)VALUES (3,20,7,'accepté','distance',1);
INSERT INTO pae.contacts (contact_school_year_id,contact_student_id,contact_company_id,contact_status,contact_refusal_reason,contact_meeting_place,contact_version)VALUES (3,7,7,'refusé','Choix autre étudiant','distance',1);





DROP TABLE IF EXISTS pae.managers;
CREATE TABLE pae.managers
(
    manager_id          SERIAL PRIMARY KEY,
    manager_lastname           TEXT NOT NULL,
    manager_firstname          TEXT NOT NULL,
    manager_phone_number        TEXT,
    manager_email               TEXT,
    manager_company_id          INTEGER REFERENCES pae.companies(company_id),
    manager_version                            INTEGER
);



INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_email,manager_company_id,manager_version)VALUES ('Dossche', 'Stéphanie', '014.54.67.54', 'stephanie.dossche@letsbuild.com',5,1);
INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_company_id,manager_version)VALUES ('ALVAREZ CORCHETE', 'Roberto', '02.566.60.14', 7,1);
INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_email,manager_company_id,manager_version)VALUES ('Assal', 'Farid', '0474 39 69 09', 'f.assal@assyst-europe.com', 1,1);
INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_company_id,manager_version)VALUES ('Ile', 'Emile', '0489 32 16 54', 4,1);
INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_company_id,manager_version)VALUES ('Hibo', 'Owln', '0456 678 567', 3,1);
INSERT INTO pae.managers (manager_lastname,manager_firstname,manager_phone_number,manager_company_id,manager_version)VALUES ('Barn', 'Henri', '02 752 17 60', 2,1);



DROP TABLE IF EXISTS pae.internships;
CREATE TABLE pae.internships
(
    internship_id                      SERIAL PRIMARY KEY,
    internship_manager_id              INTEGER REFERENCES pae.managers(manager_id),
    internship_student_id              INTEGER REFERENCES pae.users(user_id),
    internship_contact_id              INTEGER REFERENCES pae.contacts(contact_id),
    internship_company_id              INTEGER REFERENCES pae.companies(company_id),
    internship_school_year_id          INTEGER REFERENCES pae.school_years(school_year_id) NOT NULL,
    internship_topic                   TEXT,
    internship_date_of_signature       DATE,
    internship_version                 INTEGER
);

-- INTERNSHIP 2023-2024
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (1, 25, 1, 5, 4, 'Un ERP : Odoo', '10-10-23',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (2, 22, 2, 7, 4, 'sBMS project - a complex environment', '23-11-23',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (3, 23, 4, 1, 4, 'CRM : Microsoft Dynamics 365 For Sales', '12-10-23',1);


-- INTERNSHIP 2021-2022
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (4, 5, 15, 4, 2, 'Conservation et restauration d’œuvres d’art', '25-11-2021',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (2, 9, 18, 7, 2, 'L''analyste au centre du développement', '17-11-2021',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (2, 10, 19, 7, 2, 'L''analyste au centre du développement', '17-11-2021',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (3, 11, 20, 1, 2, 'ERP : Microsoft Dynamics 366', '23-11-2021',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (5, 12, 22, 3, 2, 'Entretien des rails', '22-11-2021',1);


-- INTERNSHIP 2022-2023
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (3, 16, 25, 1, 3, 'CRM : Microsoft Dynamics 365 For Sales', '23-11-2022',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (6, 14, 26, 2, 3, 'Un métier : chef de projet', '19-10-2022',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (6, 15, 27, 2, 3, 'Un métier : chef de projet', '19-10-2022',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (6, 17, 28, 2, 3, 'Un métier : chef de projet', '19-10-2022',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (6, 18, 29, 2, 3, 'Un métier : chef de projet', '19-10-2022',1);
INSERT INTO pae.internships(internship_manager_id, internship_student_id, internship_contact_id, internship_company_id, internship_school_year_id, internship_topic, internship_date_of_signature,internship_version)VALUES (2, 20, 33, 7, 3, 'sBMS project - Java Development', '17-10-2022',1);







-- 1) Comptage du nombre d’utilisateurs, par rôle et par année académique.
SELECT s.year AS school_year,
       r.role AS role_name,
       COALESCE(cnt.number_of_users, 0) AS number_of_users
FROM pae.school_years s
         LEFT JOIN (SELECT DISTINCT user_role AS role FROM pae.users) r ON 1=1
         LEFT JOIN (
    SELECT u.user_role AS role,
           u.user_school_year_id,
           COUNT(*) AS number_of_users
    FROM pae.users u
    GROUP BY u.user_role, u.user_school_year_id
) cnt ON s.school_year_id = cnt.user_school_year_id
    AND r.role = cnt.role
ORDER BY s.year, r.role;


-- 2) Année académique et comptage du nombre de stages par année académique.
SELECT s.year AS school_year,
       COALESCE(cnt.number_of_internships, 0) AS number_of_internships
FROM pae.school_years s
         LEFT JOIN (
    SELECT i.internship_school_year_id,
           COUNT(*) AS number_of_internships
    FROM pae.internships i
    GROUP BY i.internship_school_year_id
) cnt ON s.school_year_id = cnt.internship_school_year_id
ORDER BY s.year;


-- 3) Entreprise, année académique, et comptage du nombre de stages par entreprise et année académique.
SELECT c.company_name,
       s.year AS school_year,
       COALESCE(cnt.number_of_internships, 0) AS number_of_internships
FROM pae.companies c
         CROSS JOIN pae.school_years s
         LEFT JOIN (
    SELECT i.internship_company_id,
           i.internship_school_year_id,
           COUNT(*) AS number_of_internships
    FROM pae.internships i
    GROUP BY i.internship_company_id, i.internship_school_year_id
) cnt ON c.company_id = cnt.internship_company_id
    AND s.school_year_id = cnt.internship_school_year_id
ORDER BY c.company_name, s.year;


-- 4) Année académique et comptage du nombre de contacts par année académique.
SELECT s.year AS school_year,
       COALESCE(cnt.number_of_contacts, 0) AS number_of_contacts
FROM pae.school_years s
         LEFT JOIN (
    SELECT s.year,
           COUNT(*) AS number_of_contacts
    FROM pae.contacts c
             JOIN pae.school_years s ON c.contact_school_year_id = s.school_year_id
    GROUP BY s.year
) cnt ON s.year = cnt.year
ORDER BY s.year;


-- 5) États (en format lisible par le client) et comptage du nombre de contacts dans chacun des états.
SELECT st.contact_status AS status,
       COALESCE(cnt.number_of_contacts, 0) AS number_of_contacts
FROM (
         SELECT DISTINCT contact_status
         FROM pae.contacts
     ) st
         LEFT JOIN (
    SELECT contact_status,
           COUNT(*) AS number_of_contacts
    FROM pae.contacts
    GROUP BY contact_status
) cnt ON st.contact_status = cnt.contact_status
ORDER BY st.contact_status;


-- 6) Année académique, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par année académique.
SELECT s.year AS school_year,
       st.contact_status AS status,
       COALESCE(cnt.number_of_contacts, 0) AS number_of_contacts
FROM pae.school_years s
         LEFT JOIN (
    SELECT DISTINCT contact_status
    FROM pae.contacts
) st ON 1=1
         LEFT JOIN (
    SELECT s.year,
           c.contact_status,
           COUNT(*) AS number_of_contacts
    FROM pae.contacts c
             JOIN pae.school_years s ON c.contact_school_year_id = s.school_year_id
    GROUP BY s.year, c.contact_status
) cnt ON s.year = cnt.year
    AND st.contact_status = cnt.contact_status
ORDER BY s.year, st.contact_status;


-- 7) Entreprise, états (en format lisible par le client) et comptage du nombre de contacts dans chacun des états par entreprise.
SELECT com.company_name,
       s.contact_status AS status,
       COALESCE(cnt.number_of_contacts, 0) AS number_of_contacts
FROM pae.companies com
         LEFT JOIN (
    SELECT DISTINCT contact_status
    FROM pae.contacts
) s ON 1=1
         LEFT JOIN (
    SELECT com.company_name,
           c.contact_status,
           COUNT(*) AS number_of_contacts
    FROM pae.contacts c
             JOIN pae.companies com ON c.contact_company_id = com.company_id
    GROUP BY com.company_name, c.contact_status
) cnt ON com.company_name = cnt.company_name
    AND s.contact_status = cnt.contact_status
ORDER BY com.company_name, s.contact_status;