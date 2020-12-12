DROP TABLE IF EXISTS users_role;
DROP TABLE IF EXISTS users;

create sequence IF NOT EXISTS dgf_seq;

CREATE TABLE IF NOT EXISTS users (
  id bigint default dgf_seq.nextval primary key,
  user_id char(30),
  email varchar(256) NOT NULL,
  mobile char(11),
  password char(128),
  email_verified number(1),
  name varchar(64),
  picture_url varchar(256),
  locale char(2),
  family_name varchar(64),
  given_name varchar(64)
);

delete from users where id in(1, 2);

INSERT INTO users (id, user_id, email, email_verified, password) values
(dgf_seq.nextval, 'admin', 'admin', 1, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K'),
(dgf_seq.nextval, 'seo', 'seo', 1, '$2a$10$kcXK1Vjmy79dMr.T7j5AJuWAlrGTqKWu/dk7kPFYESJGHqdCdO4.K');

create table football_association (
    id bigint default dgf_seq.nextval primary key,
    unique_key varchar(256) not null,
    name varchar(128) not null,
    description varchar(256) not null
);

INSERT INTO football_association (id, unique_key, name, description) values
(dgf_seq.nextval, 'football_association.fifa', 'fifa', 'Intercontinental competitions'),
(dgf_seq.nextval, 'football_association.afc', 'afc', 'Asian competitions'),
(dgf_seq.nextval, 'football_association.caf', 'caf', 'َAfrican competitions'),
(dgf_seq.nextval, 'football_association.concacaf', 'concacaf', 'North American, Central American, and Caribbean competitions'),
(dgf_seq.nextval, 'football_association.conmebol', 'conmebol', 'South American competitions'),
(dgf_seq.nextval, 'football_association.ofc', 'ofc', 'Oceanian competitions'),
(dgf_seq.nextval, 'football_association.uefa', 'uefa', 'European competitions');

create table competition_type (
    id bigint default dgf_seq.nextval primary key,
    unique_key varchar(256) not null,
    name varchar(128) not null
);

INSERT INTO competition_type (id, unique_key, name) values
(dgf_seq.nextval, 'competition_type.knockout', 'knockout'),
(dgf_seq.nextval, 'competition_type.league', 'league'),
(dgf_seq.nextval, 'competition_type.cup', 'cup');

create table gender_type (
    id bigint default dgf_seq.nextval primary key,
    unique_key varchar(256) not null,
    name varchar(128) not null
);

INSERT INTO gender_type (id, unique_key, name) values
(dgf_seq.nextval, 'gender_type.man', 'man'),
(dgf_seq.nextval, 'gender_type.woman', 'woman');

create table country (
    id bigint default dgf_seq.nextval primary key,
    football_association_id bigint not null,
    unique_key varchar(256) not null,
    name varchar(128) not null,
    foreign key (football_association_id) references football_association(id)
);

INSERT INTO country (id, football_association_id, unique_key, name) values
(dgf_seq.nextval, (select id from football_association where name = 'afc'), 'country.iran', 'iran'),
(dgf_seq.nextval, (select id from football_association where name = 'uefa'), 'country.england', 'england'),
(dgf_seq.nextval, (select id from football_association where name = 'uefa'), 'country.spain', 'spain');

create table league (
    id bigint default dgf_seq.nextval primary key,
    football_association_id bigint not null,
    country_id bigint not null,
    gender_type_id bigint not null,
    unique_key varchar(256) not null,
    name varchar(128) not null,
    foreign key (football_association_id) references football_association(id),
    foreign key (country_id) references country(id),
    foreign key (gender_type_id) references gender_type(id)
);

INSERT INTO league (id, football_association_id,country_id, unique_key, name) values
(dgf_seq.nextval,
 (select id from football_association where name = 'afc'),
 (select id from country where name = 'iran'),
 (select id from gender_type where name = 'man'),
  'league.persian_golf', 'persian golf');

create table player (
    id bigint default dgf_seq.nextval primary key,
    football_association_id bigint not null,
    country_id bigint not null,
    gender_type_id bigint not null,
    unique_key varchar(256) not null,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    foreign key (football_association_id) references football_association(id),
    foreign key (country_id) references country(id),
    foreign key (gender_type_id) references gender_type(id)
);

create table club (
    id bigint default dgf_seq.nextval primary key,
    football_association_id bigint not null,
    country_id bigint not null,
    gender_type_id bigint not null,
    unique_key varchar(256) not null,
    name varchar(128) not null,
    last_name varchar(128) not null,
    foreign key (football_association_id) references football_association(id),
    foreign key (country_id) references country(id),
    foreign key (gender_type_id) references gender_type(id)
);

create table membership (
    id bigint default dgf_seq.nextval primary key,
    club_id bigint not null,
    player_id bigint not null,
    unique_key varchar(256) not null,
    name varchar(128) not null,
    last_name varchar(128) not null,
    foreign key (club_id) references club(id),
    foreign key (player_id) references player(id)
);

