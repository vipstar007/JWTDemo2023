create table token
(
    id         integer not null
        primary key,
    expired    boolean not null,
    revoked    boolean not null,
    token      text    not null
        constraint uk_pddrhgwxnms2aceeku9s2ewy5
            unique,
    token_type varchar(255),
    user_id    integer
        constraint fkiblu4cjwvyntq3ugo31klp1c6
            references _user
);

alter table token
    owner to postgres;

