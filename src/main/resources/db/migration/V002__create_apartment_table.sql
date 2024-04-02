create sequence apartment_application_sequence start 2 increment 1;

create table if not exists apartment_application
(
    id          int8 PRIMARY KEY NOT NULL,
    value_rooms varchar,
    rating      varchar,
    amount      varchar,
    smoking     bool,
    floor       int4,
    availability bool,
    address_id  int8 REFERENCES address_apartment_application (id)
);

insert into apartment_application (id, value_rooms, rating, amount, smoking, floor, availability, address_id)
VALUES (1, '2', 'A', '1000', true, 4, true, 1);