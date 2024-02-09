-- CREAZIONE SCHEMA tournament e user_util
create schema tournament;
create schema user_util;
create schema reservation;


-- CREAZIONE TIPO: TOKEN_TYPE
CREATE TYPE user_util.token_type AS ENUM
    ('BEARER');

CREATE CAST (character varying AS user_util.token_type) WITH INOUT AS ASSIGNMENT;

-- TABELLA GIOCATORI
create table tournament.player (
    id serial,
    score bigint default 500,
    constraint player_pk primary key (id)
);

-- TABELLA UTENTI SITO
create table user_util._user (
	id serial,
	name varchar(32) not null,
	surname varchar(32) not null,
	email varchar(48) not null,
	password varchar(512),
	registration_date timestamp,
	last_login timestamp,
	player_id bigint unique,
	role varchar(16),
	constraint user_pk primary key (id),
	constraint user_player_id_fk foreign key (player_id) references tournament.player(id)
);

-- TABELLA TOKEN
create table user_util.token (
	id serial,
	token varchar(1024) unique,
	type_token user_util.token_type,
	revoked boolean,
	expired boolean,
	user_id bigint not null,
	constraint token_pk primary key (id),
	constraint token_user_id_fk foreign key (user_id) references user_util._user(id)
);

----------------------------------------------RESERVATION---------------------------------------------------

create table reservation.domain_sport(
    sport varchar(255) not null,
    constraint domain_sport_pk primary key (sport)
);

create table reservation.place (
    id serial,
    name varchar,
    address varchar,
    city varchar,
    province varchar,
    region varchar,
    manager_id bigint,
    constraint place_pk primary key (id),
    constraint place_unique unique (name, address, city),
    constraint place_manager_fk foreign key (manager_id) references user_util._user(id)
);

create table reservation.field(
    id serial,
    id_place bigint,
    sport varchar,
    constraint field_pk primary key (id),
    constraint field_place_fk foreign key (id_place) references reservation.place(id),
    constraint field_sport_fk foreign key (sport) references reservation.domain_sport(sport)
);

create table reservation.schedule_prop (
    id serial,
    id_field bigint,
    start_time time(0),
    end_time time(0),
    duration bigint,
    day_number int,
    constraint schedule_prop_pk primary key (id),
    constraint schedule_prop_field_fk foreign key (id_field) references reservation.field(id)
);

create table reservation.reservation (
    id serial,
    id_field bigint,
    user_id bigint,
    reservation_date date,
    reservation_start time,
    reservation_end time,
    constraint reservation_pk primary key (id),
    constraint reservation_field_fk foreign key (id_field) references reservation.field(id),
    constraint reservation_user_fk foreign key (user_id) references user_util._user(id)
);

---------------------------------------------FINE RESERVATION-----------------------------------------------


-- TABELLA TEAM
create table tournament.team (
    id serial,
    team_name varchar(255) not null,
    team_leader bigint not null,
    score bigint,
    constraint team_pk primary key (id),
    constraint team_name_unique unique (team_name),
    constraint team_leader_fk foreign key (team_leader) references tournament.player(id)
);

--TABELLA RELAZIONE GIOCATORI-TEAM
create table tournament.team_component (
    id serial,
    team_id bigint not null,
    player_id bigint not null,
    status int default 2,
    constraint team_component_pk primary key (id),
    constraint team_component_unique unique(team_id, player_id),
    constraint team_compo_team_fk foreign key (team_id) references tournament.team(id),
    constraint team_compo_player_fk foreign key (player_id) references tournament.player(id)
);

-- TABELLA DOMINIO DEI LUOGHI DOVE SI SVOLGONO I TORNEI
create table tournament.domain_place_tournament(
    place varchar(255) not null,
    address varchar(255),
    field_number int not null,
    constraint domain_place_tournament_pk primary key (place)
);

-- TABELLA DOMINIO DEI TIPI DI TORNEO
create table tournament.domain_type_tournament(
    tournament_type_name varchar(20) not null, --DA CAMBIARE NOME FA SCHIFOOOOO
    tournament_description varchar(255),
    constraint domain_type_tournament_pk primary key (tournament_type_name)
);

-- TABELLA TORNEO
create table tournament.tournament (
    id serial,
    tournament_name varchar(255) not null,
    start_date timestamp,
    end_date timestamp,
    place varchar(255),
    tournament_type varchar(20),
    _user bigint,
    status int,
    constraint tournament_pk primary key (id),
    constraint tournament_user_fk foreign key (_user) references user_util._user(id),
    constraint tournament_type_fk foreign key (tournament_type) references tournament.domain_type_tournament(tournament_type_name),
    constraint tournament_place_fk foreign key (place) references tournament.domain_place_tournament(place)
);

--aggiunta importante!!!! va bene?
--TABELLA CLASSIFICA FASE A GIRONI
create table tournament.group_stage_standing(
    id serial,
    tournament_id bigint not null,
    team_id bigint not null,
    group_stage int,
    point_scored int default 0,
    point_conceded int default 0,
    standing int default 0,
    points int default 0,
    constraint group_stage_standing_pk primary key (id),
    constraint group_stage_tournament_fk foreign key (tournament_id) references tournament.tournament (id) on delete cascade,
    constraint group_stage_team_fk foreign key (team_id) references tournament.team
);


--TABELLA TEAM ISCRITTI AL TORNEO
create table tournament.team_in_tournament(
    id serial,
    round int,
    team_id bigint not null,
    tournament_id bigint not null,
    status int default 2,
    constraint team_in_tournament_pk primary key (id),
    constraint team_in_tournament_unique unique (team_id, tournament_id),
    constraint team_in_tournament_team_fk foreign key (team_id) references tournament.team,
    constraint team_in_tournament_tournament_fk foreign key (tournament_id) references tournament.tournament on delete cascade
);

create table tournament.domain_match_type(
    type varchar(255) not null, --DA CAMBIARE NOME FA SCHIFOOOOO
    description varchar(255),
    constraint domain_match_type_pk primary key (type)
);

-- TABELLA PARTITE
create table tournament.match (
    id serial,
    match_number int,
    match_type varchar(255),
    group_stage int,
    tournament_id bigint,
    home_team_id bigint,
    away_team_id bigint,
    field_number int,
    start_date timestamp,
    user_id bigint,
    --risultato_finale varchar(20),
    winner_team boolean, -- prima era references a team
    constraint match_pk primary key (id),
    constraint match_description_fk foreign key(match_type) references tournament.domain_match_type(type),
    constraint match_home_team_fk foreign key (home_team_id) references tournament.team(id),
    constraint match_away_team_fk foreign key (away_team_id) references tournament.team(id),
    constraint match_tournament_id_fk foreign key (tournament_id) references tournament.tournament(id) on delete cascade,
    constraint match_user_fk foreign key (user_id) references user_util._user(id)
);

create table tournament.set_match(
    id serial,
    match_id int not null,
    set_number int not null,
    home_team_score int,
    away_team_score int,
    constraint set_match_pk primary key (id),
    constraint set_match_unique unique (match_id, set_number),
    constraint id_match_fk foreign key (match_id) references tournament.match(id) on delete cascade
);

create table tournament.pizza_order(
    id serial,
    tournament_id bigint not null,
    order_date timestamp,
    constraint pizza_order_pk primary key (id),
    constraint tournament_id_fk foreign key (tournament_id) references tournament.tournament(id) on delete cascade
);

create table tournament.pizza_order_line(
    id serial,
    pizza_order_id bigint not null,
    player_id bigint not null,
    pizza_type varchar(255),
    --pizza_cost numeric(2,2), ---??Lo mettiamo?
    quantity int,
    constraint pizza_order_line_pk primary key (id),
    constraint pizza_order_id_fk foreign key (pizza_order_id) references tournament.pizza_order(id),
    constraint player_id_fk foreign key (player_id) references tournament.player(id)
);

--insert into user_util._user (id, name, surname, email, password, registration_date, last_login, player_id, role) values
--(1, 'Nicola', 'Piacq', 'nicopiacq@mail.com', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'SUPERADMIN'),
--(2, 'Ciro', 'Mata', 'ciromata@mail.com', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'USER'),
--(3, 'Fede', 'Guai', 'fedeguai@mail.com', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'SUPERADMIN'),
--(4, 'Elettra', 'Lambo', 'elettralambo@mail.com', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'USER'),
--(5, 'Pino', 'Fastidio', 'pinofastidio@mail.com', null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, null, 'ADMIN');

insert into tournament.domain_type_tournament (tournament_type_name, tournament_description) values
('10-corto', 'Torneo composto da 10 squadre, 2 gironi da 5, seconda fase corta ...'),
('10-lungo', 'Torneo composto da 10 squadre, 2 gironi da 5, seconda fase lunga ...');

insert into tournament.domain_place_tournament (place, field_number) values
('generation', 2),
('Pietra Ligure', 2),
('Loano', 2);

--insert into tournament.tournament (tournament_name, place, tournament_type) values
--('torneo prova - 10-corto', 'generation', '10-corto'),
--('torneo prova - 10-lungo', 'generation', '10-lungo');

--insert into tournament.player (score) values
--(400),(400),(400);
--
--insert into tournament.team (team_name, team_leader) values
--('team 1', 1), ('team 2', 2), ('team 3', 3),
--('team 4', 1), ('team 5', 1), ('team 6', 1), ('team 7', 1),
--('team 8', 1), ('team 9', 1), ('team 10', 1);
--
--insert into tournament.team_in_tournament(team_id, tournament_id)values
--(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1),
--(1, 2), (2, 2), (3, 2), (4, 2), (5, 2), (6, 2), (7, 2), (8, 2), (9, 2), (10, 2);

insert into tournament.domain_match_type (type, description) values
('GIRONE', 'Partita di un girone wow che descrizione'),
('OTTAVI', 'Ottavi di Finale'),
('QUARTI1x8', 'Quarti di Finale primo - ottavo posto'),
('SEMIFINALE1x4', 'Semifinale primo - quarto posto' ),
('SEMIFINALE5x8', 'Semifinale quinto - ottavo posto' ),
('FINALE1x2', 'Finale primo - secondo posto! Sei un campione se giochi questa partita'),
('FINALE3x4', 'Finale terzo - quarto posto'),
('FINALE5x6', 'Finale quinto - sesto posto'),
('FINALE7x8', 'Finale settimo - ottavo posto'),
('FINALE9x10', 'Finale nono - decimo posto'),
('FINALE11x12', 'Finale undicesimo - dodicesimo posto');

insert into reservation.reservation (reservation_date) values
(CURRENT_DATE);

insert into reservation.domain_sport values
('BEACHVOLLEY');