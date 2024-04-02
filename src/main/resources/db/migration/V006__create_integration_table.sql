create table if not exists integration_info(
    tag varchar PRIMARY KEY NOT NULL,
    url varchar,
    permission_key varchar
);

insert into integration_info (tag, url, permission_key)
VALUES ('GEO', 'https://api.opencagedata.com/geocode/v1/json?q=%s+%s&key=%s&language=ru', 'N2NjNTQzYmI0NWNkNGU5MTg3YjVmYzFiODY2ZWUyYmU='),
       ('PRODUCT', 'http://localhost:9096/api/product?id=%s&key=%s','TWFyZ2FyaXRh');

