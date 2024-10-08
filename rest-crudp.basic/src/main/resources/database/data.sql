DROP TABLE IF EXISTS character;

DROP SEQUENCE IF EXISTS character_id_seq;

CREATE SEQUENCE character_id_seq;

CREATE TABLE character
(
    id         integer PRIMARY KEY DEFAULT nextval('character_id_seq'),
    code       varchar(6)  NOT NULL,
    firstname  varchar(32),
    lastname   varchar(32) NOT NULL,
    appearance integer     NOT NULL
);

ALTER SEQUENCE character_id_seq OWNED BY character.id;
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ash-Cr', 'Ash', 'Crimson', 2003);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ath-As', 'Athena', 'Asamiya', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ben-Ni', 'Benimaru', 'Nikaido', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Bil-Ka', 'Billy', 'Kane', 1995);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Chi-Ka', 'Chizuru', 'Kagura', 1996);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Dai-Go', 'Daimon', 'Goro', 1995);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Duo-Lo', 'Duo', 'Lon', 2001);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Eli-Br', 'Elisabeth', 'Bridget', 2003);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Gor-Da', 'Goro', 'Daimon', 1995);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Hwa-Ja', 'Hwa', 'Jai', 1996);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ior-Ya', 'Iori', 'Yagami', 1995);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Jen-Bl', 'Jenet', 'Blue', 2003);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Xia-Li', 'Xiangfei', 'Li', 1999);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ken-Sh', 'Kensou', 'Sheng', 1996);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Kim-Ka', 'Kim', 'Kaphwan', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Kul-Di', 'Kula', 'Diamond', 2000);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Kyo-Ku', 'Kyo', 'Kusanagi', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Leo-He', 'Leona', 'Heidern', 1996);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Mai-Sh', 'Mai', 'Shiranui', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Rob-Ga', 'Robert', 'Garcia', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Roc-Ho', 'Rock', 'Howard', 1999);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Rug-Be', 'Rugal', 'Bernstein', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ryo-Sa', 'Ryo', 'Sakazaki', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Sai-Ku', 'Saisyu', 'Kusanagi', 1995);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'She-Wo', 'Shen', 'Woo', 2003);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'She-Fl', 'Shermie', 'Fleur', 1997);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Shi-Ya', 'Shingo', 'Yabuki', 1996);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Ter-Bo', 'Terry', 'Bogard', 1994);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Yas-Na', 'Yashiro', 'Nanakase', 1997);
INSERT INTO character
VALUES (nextval('character_id_seq'), 'Yur-Sa', 'Yuri', 'Sakazaki', 1994);