create sequence client_application_sequence start 1 increment 1;

create table if not exists client_application
(
    id int8 PRIMARY KEY NOT NULL,
    nike_name varchar,
    email varchar,
    password varchar,
    token_id int8 REFERENCES token_client (id)
);