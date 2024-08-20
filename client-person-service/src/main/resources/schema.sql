CREATE SCHEMA if not exists "catalog";

CREATE TABLE if not exists catalog.person (
    per_id uuid NOT NULL,
    per_address varchar(255) NULL,
    per_age int4 NULL,
    per_gender varchar(255) NULL,
    per_identification varchar(255) NULL,
    per_name varchar(255) NULL,
    per_phone varchar(255) NULL,
    CONSTRAINT person_pkey PRIMARY KEY (per_id)
);

CREATE TABLE if not exists catalog.client (
    cli_id uuid NOT NULL,
    cli_password varchar(255) NULL,
    cli_state bool NULL,
    per_id uuid NULL,
    CONSTRAINT client_pkey PRIMARY KEY (cli_id),
    CONSTRAINT person_u UNIQUE (per_id),
    CONSTRAINT person_fkey FOREIGN KEY (per_id) REFERENCES catalog.person(per_id)
);