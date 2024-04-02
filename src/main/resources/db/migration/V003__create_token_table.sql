create sequence token_client_sequence start 1 increment 1;

create table if not exists token_client
(
    id int8 PRIMARY KEY NOT NULL,
    token_csrf varchar
);
