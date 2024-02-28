DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;
CREATE TABLE pae.utilisateur
(
    id_utilisateur   SERIAL PRIMARY KEY,
    email            TEXT NOT NULL,
    mot_de_passe     TEXT NOT NULL,
    nom              TEXT NOT NULL,
    prenom           TEXT NOT NULL,
    numero_tel       TEXT NOT NULL,
    date_inscription DATE        NOT NULL,
    role_utilisateur CHAR(1)     NOT NULL
);

INSERT INTO pae.utilisateur (email, mot_de_passe, nom, prenom, numero_tel, date_inscription, role_utilisateur) VALUES ('admin@vinci.be','$2a$10$nObdnHRJccZsy6HXwGVv7OgJbR93rYoMwo1b3s/jA13FCo8W0KN3u','admin','admin','0489055536',date(now()),'A');
INSERT INTO pae.utilisateur (email, mot_de_passe, nom, prenom, numero_tel, date_inscription, role_utilisateur) VALUES ('resul.ramadani@student.vinci.be','$2a$10$nObdnHRJccZsy6HXwGVv7OgJbR93rYoMwo1b3s/jA13FCo8W0KN3u','Resul','Ramadani','0489055536',date(now()),'E');