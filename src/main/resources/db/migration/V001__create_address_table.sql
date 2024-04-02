create sequence address_apartment_application_sequence start 2 increment 1;

create table if not exists address_apartment_application(
    id int8 PRIMARY KEY NOT NULL,
    name_street varchar,
    name_city varchar,
    number_house varchar
);

insert into address_apartment_application (id, name_street, name_city, number_house)
VALUES (1, 'Августовская', 'Апрелевка', 2);