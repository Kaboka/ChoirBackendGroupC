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
    GROUP_NAME varchar(40),
    NAME varchar(40)
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
    TYPE varchar(10),
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
Values(1,'ChoirMember','Lars','Clausen','1957-02-15');

insert into MEMBER
values(1,'Ålegade 7','2300','København','53246291','Lars_Clausen@hotmail.com','12345',1);

insert into HAS_ROLE
values
(1,'ADM');