-- CREAZIONE SCHEMA tournament
create schema tournament;

-- TABELLA GIOCATORI
create table tournament.player (
    id serial,
    nickname varchar(64) not null,
    score bigint,
    --nome varchar(255) not null,
    --cognome varchar(255) not null,
    --data_nascita date,
    constraint player_pk primary key (id)
    );

-- TABELLA TEAM
create table tournament.team (
    id serial,
    team_name varchar(255) not null,
    team_leader bigint not null,
    score int,
    constraint team_pk primary key (id),
    constraint team_name_unique unique (team_name),
    constraint team_leader_fk foreign key (team_leader) references tournament.player(id)
);

--TABELLA RELAZIONE GIOCATORI-TEAM
create table tournament.team_component (
    id serial,
    team_id bigint not null,
    player_id bigint not null,
    constraint team_component_pk primary key (id),
    constraint team_component_unique unique(team_id, player_id),
    constraint team_compo_team_fk foreign key (team_id) references tournament.team(id),
    constraint team_compo_player_fk foreign key (player_id) references tournament.player(id)
);

-- TABELLA DOMINIO DEI LUOGHI DOVE SI SVOLGONO I TORNEI
create table tournament.domain_place_tournament(
    place varchar(255) not null,
    adress varchar(255),
    field_number int not null,
    constraint domain_place_tournament_pk primary key (place)
);

-- TABELLA DOMINIO DEI TIPI DI TORNEO
create table tournament.domain_type_tournament(
    tournament_type varchar(20) not null, --DA CAMBIARE NOME FA SCHIFOOOOO
    tournament_description varchar(255),
    constraint domain_type_tournament_pk primary key (tournament_type)
);

-- TABELLA TORNEO
create table tournament.tournament (
    id serial,
    tournament_name varchar(255) not null,
    start_date timestamp,
    end_date timestamp,
    place varchar(255),
    tournament_type varchar(20) not null,
    constraint tournament_pk primary key (id),
    constraint tournament_type_fk foreign key (tournament_type) references tournament.domain_type_tournament(tournament_type),
    constraint tournament_place_fk foreign key (place) references tournament.domain_place_tournament(place)
);

--TABELLA TEAM ISCRITTI AL TORNEO
create table tournament.team_in_tournament(
    id serial,
    team_id bigint not null,
    tournament_id bigint not null,
    constraint team_in_tournament_pk primary key (id),
    constraint team_in_tournament_unique unique (team_id, tournament_id),
    constraint team_in_tournament_team_fk foreign key (team_id) references tournament.team,
    constraint team_in_tournament_tournament_fk foreign key (tournament_id) references tournament.tournament
);

-- TABELLA PARTITE
create table tournament.match (
    id serial,
    match_number int not null,
    tournament_id bigint,
    home_team_id bigint,
    away_team_id bigint,
    field_number int,
    start_date timestamp,
    --risultato_finale varchar(20),
    winner_team_id bigint,
    constraint match_pk primary key (id),
    constraint home_team_fk foreign key (home_team_id) references tournament.team(id),
    constraint away_team_fk foreign key (away_team_id) references tournament.team(id),
    constraint winner_team_fk foreign key (winner_team_id) references tournament.team(id),
    constraint tournament_id_fk foreign key (tournament_id) references tournament.tournament(id)
);

create table tournament.set_match(
    id serial,
    match_id int not null,
    set_number int not null,
    home_team_score int,
    away_team_score int,
    constraint set_match_pk primary key (id),
    constraint set_match_unique unique (match_id, set_number),
    constraint id_match_fk foreign key (match_id) references tournament.match(id)
);

create table tournament.pizza_order(
    id serial,
    tournament_id bigint not null,
    order_date timestamp,
    constraint pizza_order_pk primary key (id),
    constraint tournament_id_fk foreign key (tournament_id) references tournament.tournament(id)
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