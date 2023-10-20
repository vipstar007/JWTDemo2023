create table airport_codes
(
    ident        text,
    type         text,
    name         text,
    elevation_ft integer,
    continent    text,
    iso_country  text,
    iso_region   text,
    municipality text,
    gps_code     text,
    iata_code    text,
    local_code   text,
    coordinates  text
);

alter table airport_codes
    owner to postgres;

