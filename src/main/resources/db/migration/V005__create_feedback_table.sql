create sequence feedback_application_sequence start 2 increment 1;

create table if not exists feedback_application(
    id int8 PRIMARY KEY NOT NULL,
    rating varchar,
    comment varchar,
    apartment_id int8 REFERENCES apartment_application (id)
);

insert into feedback_application (id, rating, comment, apartment_id)
VALUES (1, '5', 'хорошие аппартаменты', 1);
