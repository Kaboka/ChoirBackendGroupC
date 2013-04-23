drop table HAS_VOICE;
drop table SHEET;
drop table AUDIO;
drop table MATERIAL;
drop table MUSIC;
drop table ARTIST;
drop table HAS_ROLE;
drop table MEMBER;
drop table VOICE;
drop table CHOIR_ROLE;
drop table PERSON;

drop sequence CHOIR_SEQUENCE restrict;

create sequence CHOIR_SEQUENCE as int minvalue 1;

create table PERSON(
    ID int primary key,
    DTYPE varchar(20),
    FIRST_NAME varchar(30) not null,
    LAST_NAME varchar(30) not null,
    DATE_OF_BIRTH date
);

create table CHOIR_ROLE(
    CODE varchar(3) primary key,
    NAME varchar(40)
);

create table VOICE(
    CODE int primary key,
    NAME varchar(40),
    GROUP_NAME varchar(40)
);


create table MEMBER(
    ID int primary key references PERSON(ID),
    STREET varchar(40),
    ZIPCODE varchar(4),
    CITY varchar(20),
    PHONE varchar(30),
    EMAIL varchar(40),
    PASSWORD varchar(30),
    CODE int references VOICE(CODE)
);


create table HAS_ROLE(
    ID int references MEMBER(ID),
    CODE varchar(3) references CHOIR_ROLE(CODE),
    primary key(ID,CODE)
);

create table ARTIST(
    ID int primary key references PERSON(ID),
    DATE_OF_DEATH date,
    COUNTRY varchar(20),
    WIKI_URL varchar(200)
);

create table MUSIC(
    ID int primary key,
    TITLE varchar(40),
    OPUS int,
    RELEASE_YEAR int,
    PLACE varchar(40),
    HISTORY varchar(300),
    COMPOSER int not null references ARTIST(ID)
);

create table MATERIAL(
    ID int primary key,
    DTYPE varchar(20),
    TITLE varchar(40),
    FILE varchar(50),
    FILE_SIZE int,
    MUSIC_ID int not null references MUSIC(ID)
);

create table AUDIO(
    ID int primary key references MATERIAL(ID),
    PLAYING_TIME int
);

create table SHEET(
    ID int primary key references MATERIAL(ID),
    PAGE_COUNT int
);

create table HAS_VOICE(
    CODE int references VOICE(CODE),
    ID int references MATERIAL(ID),
    primary key(CODE,ID)
);

insert into VOICE
values
(0,'None','Other'),
(1,'First Soprano','Soprano'),
(2,'Second Soprano','Soprano'),
(4,'First Alto','Alto'),
(8,'Second Alto','Alto'),
(16,'First Tenor','Tenor'),
(32,'Second Tenor','Tenor'),
(64,'First Bass','Bass'),
(128,'Second Bass','Bass'),
(256,'Conductor','Other');

insert into CHOIR_ROLE
values
('ADM','Administrator'),
('BRD','Board'),
('REP','Repertoire');

insert into PERSON
Values(1,'ChoirMember','Anders','Kalhauge','1959-11-14');
insert into PERSON
Values(2,'ChoirMember','Jette','Kreiner-Møller','1949-10-21');
insert into PERSON
Values(3,'ChoirMember','Lars','Mortensen','1965-01-23');
insert into PERSON
Values(4,'ChoirMember','Henrik','Bulskov','1970-02-28');
insert into PERSON
Values(5,'ChoirMember','Tine','Marbjerg','1962-03-23');
insert into PERSON
Values(6,'ChoirMember','Henrik','Hauge','1961-04-15');
insert into PERSON
Values(7,'ChoirMember','Torben','Østrup','1951-05-22');
insert into PERSON
Values(8,'ChoirMember','Palle','Bech','1957-02-05');
insert into PERSON
Values(9,'ChoirMember','Caroline','Hundahl','1951-05-22');
insert into PERSON
Values(10,'ChoirMember','Peter','Lorensen','1975-10-17');
insert into PERSON
Values(11,'ChoirMember','Lars','Bogetoft','1960-05-17');
insert into PERSON
Values(12,'Artist','Wolfgang Amadeus','Mozart','1756-01-27');
insert into PERSON
values(13, 'Artist', 'Ludwig Van', 'Beethoven', '1770-12-17');
insert into PERSON
values(14, 'Artist', 'Johann Sebastian', 'Bach', '1685-03-31');
insert into PERSON
values(15, 'Artist', 'Peter Ilyich', 'Tchaikovsky', '1840-05-07');

insert into MEMBER
values(1,'Skrænten 5','3600','Frederikssund','21 72 44 11','aka@cphbusiness.dk','aka',64);
insert into MEMBER
values(2,'Slåenbakke Allé 1','3600','Frederikssund','12 34 56 78','jekm@cphbusiness.dk','jekm',4);
insert into MEMBER
values(3,'Hjemmevej 7','4711','Nykøbing Lolland','31 41 59 26','lam@cphbusiness.dk','lam',32);
insert into MEMBER
values(4,'Domkirkevej 8','4000','Roskilde','12 35 81 32','hsty@cphbusiness.dk','hsty',16);
insert into MEMBER
values(5,'Theodorsvej 17','2820','Gentofte','60 22 14 18','tm@cphbusiness.dk','tm',2);
insert into MEMBER
values(6,'Sekvensdiagrammet 8','2750','Kgs. Ballerup','27 18 28 18','hau@cphbusiness.dk','hau',128);
insert into MEMBER
values(7,'Actionscript Allé 15','2750','Kgs. Ballerup','11 22 33 44','tor@cphbusiness.dk','tor',128);
insert into MEMBER
values(8,'Frederiksvej 10','2970','Hørsholm','79 13 79 13','pab@cphbusiness.dk','pab',32);
insert into MEMBER
values(9,'Perspektivvej 42','2750','Kgs. Ballerup','87 65 43 21','chu@cphbusiness.dk','chu',2);
insert into MEMBER
values(10,'Somewhereinus 117','9999','Abroad','112','pelo@cphbusiness.dk','pelo',128);
insert into MEMBER
values(11,'Skovgårdssidegade 6','2920','Charlottenlund','18 27 36 45','lbp@cphbusiness.dk','lbp',64);

insert into HAS_ROLE
values
(1,'ADM');
insert into HAS_ROLE
values
(3,'ADM');
insert into HAS_ROLE
values
(5,'BRD');
insert into HAS_ROLE
values
(1,'REP');
insert into HAS_ROLE
values
(2,'REP');
insert into HAS_ROLE
values
(10,'REP');
insert into HAS_ROLE
values
(11,'BRD');

insert into ARTIST
values
(12, '1791-12-5','Austria','http://en.wikipedia.org/wiki/Wolfgang_Amadeus_Mozart');
insert into ARTIST
values
(13, '1827-03-26', 'Germany', 'http://en.wikipedia.org/wiki/Ludwig_van_Beethoven');
insert into ARTIST
values
(14, '1750-07-28', 'Germany', 'http://en.wikipedia.org/wiki/Johann_Sebastian_Bach');
insert into ARTIST
values
(15, '1893-11-06', 'Russia', 'http://en.wikipedia.org/wiki/Pyotr_Ilyich_Tchaikovsky');

insert into MUSIC
values(1, 'Symphony No. 19', 19, 1772, 'Milan', 'Written on his tour in Milan in October 1772- The composition is in E-flat Major', 12);
insert into MUSIC
values(2, 'Symphony No. 18', 18, 1773, 'Paris', 'Written on his tour in Paris in October 1773- The composition is in D Major', 12);
insert into MUSIC
values(3, 'Symphony No. 17', 17, 1777, 'Vienna', 'Written on his tour in Auswitch in October 1772- The composition is in D-flat Major', 12);
insert into MUSIC
values(4, 'Flight of the Bumblebee', 5, 1800, 'Munich', 'Fast-paced composition with alot of piano and violin', 13);
insert into MUSIC
values(5, 'Symphony No. 7', 7, 1798, 'Berlin', 'Soft and slow-paced', 13);
insert into MUSIC
values(6, 'Fur Elise', 1, 1785, 'Koln', 'A composition written for a girl named Elise', 13);
insert into MUSIC
values(7, 'Symphony No. 2', 2, 1705, 'Puttgarden', 'Bach wrote this composition while he were buying beer on the cruiseship', 14);
insert into MUSIC
values(8, 'Vodka No. 9', 9, 1890, 'St. Petersburg', 'Very good', 15);

insert into MATERIAL
values(1, 'Sheet', 'Symphony No. 19', 'SymphonyNo19.txt', 20, 1);
insert into MATERIAL
values(2, 'Audio', 'Symphony No. 19', 'SymphonyNo19.mp3', 200, 1);
insert into MATERIAL
values(3, 'Sheet', 'Symphony No. 18', 'SymphonyNo18.txt', 20, 2);
insert into MATERIAL
values(4, 'Audio', 'Symphony No. 18', 'SymphonyNo18.mp3', 200, 2);
insert into MATERIAL
values(5, 'Sheet', 'Symphony No. 17', 'SymphonyNo17.txt', 20, 3);
insert into MATERIAL
values(6, 'Audio', 'Symphony No. 17', 'SymphonyNo17.mp3', 200, 3);
insert into MATERIAL
values(7, 'Sheet', 'Flight of the Bumblebee', 'FlightoftheBumblebee.txt', 20, 4);
insert into MATERIAL
values(8, 'Audio', 'Flight of the Bumblebee', 'FlightoftheBumblebee.mp3', 200, 4);
insert into MATERIAL
values(9, 'Sheet', 'Symphony No. 7', 'SymphonyNo7.txt', 20, 5);
insert into MATERIAL
values(10, 'Audio', 'Symphony No. 7', 'SymphonyNo7.mp3', 200, 5);
insert into MATERIAL
values(11, 'Sheet', 'Fur Elise', 'FurElise.txt', 20, 6);
insert into MATERIAL
values(12, 'Audio', 'Fur Elise', 'FurElise.mp3', 200, 6);
insert into MATERIAL
values(13, 'Sheet', 'Symphony No. 2', 'SymphonyNo2.txt', 20, 7);
insert into MATERIAL
values(14, 'Audio', 'Symphony No. 2', 'SymphonyNo2.mp3', 200, 7);
insert into MATERIAL
values(15, 'Sheet', 'Vodka No. 9', 'VodkaNo9.txt', 20, 8);
insert into MATERIAL
values(16, 'Audio', 'Vodka No. 9', 'VodkaNo9.mp3', 200, 8);

insert into AUDIO
values(1, 305);
insert into AUDIO
values(3, 685);
insert into AUDIO
values(5, 784);
insert into AUDIO
values(7, 567);
insert into AUDIO
values(9, 839);
insert into AUDIO
values(11, 637);
insert into AUDIO
values(13, 345);
insert into AUDIO
values(15, 657);

insert into SHEET
values(2, 12);
insert into SHEET
values(4, 10);
insert into SHEET
values(6, 9);
insert into SHEET
values(8, 15);
insert into SHEET
values(10, 14);
insert into SHEET
values(12, 12);
insert into SHEET
values(14, 13);
insert into SHEET
values(16, 8);

