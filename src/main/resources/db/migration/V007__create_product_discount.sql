create sequence product_discount_sequence start 7 increment 1;

CREATE TABLE if not exists product_discount
(
    id int8 PRIMARY KEY NOT NULL,
    discount varchar,
    value int4
);

INSERT INTO product_discount(id, discount, value)
VALUES (1, 'простая скидка', 5);

INSERT INTO product_discount(id, discount, value)
VALUES (2, 'скидка на новые аппартаменты', 10);

INSERT INTO product_discount(id, discount, value)
VALUES (3, 'сезонная скидка', 15);

INSERT INTO product_discount(id, discount, value)
VALUES (4, 'специальная скидка', 20);

INSERT INTO product_discount(id, discount, value)
VALUES (5, 'скидка на праздник 9 мая', 12);

INSERT INTO product_discount(id, discount, value)
VALUES (6, 'скидка на бронь по понедельникам', 7);