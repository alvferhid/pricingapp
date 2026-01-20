-- BRAND

INSERT INTO brand (brand_name) VALUES ('Zara');
INSERT INTO brand (brand_name) VALUES ('Pull And Bear');
INSERT INTO brand (brand_name) VALUES ('Bershka');

-- PRODUCT

INSERT INTO product (id, product_name) VALUES (35455, 'jacket');
INSERT INTO product (id, product_name) VALUES (35456, 'trouser');
INSERT INTO product (id, product_name) VALUES (35457, 'skirt');

-- PRICES

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES (1, '2020-06-14 00.00.00', '2020-12-31 23.59.59', 1, 35455
    , 0, 35.5, 'EUR');
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES (1, '2020-06-14 15.00.00', '2020-06-14 18.30.00', 2, 35455
        , 1, 25.45, 'EUR');
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES (1, '2020-06-15 00.00.00', '2020-06-15 11.00.00', 3, 35455
        , 1, 30.5, 'EUR');
INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr) VALUES (1,' 2020-06-15 16.00.00', '2020-12-31 23.59.59', 4, 35455
        , 1,38.95, 'EUR');