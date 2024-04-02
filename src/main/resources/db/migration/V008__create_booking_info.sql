create sequence booking_info_sequence start 1 increment 1;
CREATE TABLE if not exists booking_info
(
    id int8 PRIMARY KEY NOT NULL,
    date_start timestamp,
    date_end timestamp,
    apartment_id int8 REFERENCES apartment_application (id),
    client_id int8 REFERENCES client_application (id),
    product_id int8 REFERENCES product_discount (id)
);