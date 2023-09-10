create table _user
(
    id        integer not null
        primary key,
    email     varchar(255),
    firstname varchar(255),
    lastname  varchar(255),
    password  varchar(255),
    role      varchar(255)
);

alter table _user
    owner to postgres;

