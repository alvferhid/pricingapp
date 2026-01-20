DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS prices;

CREATE TABLE IF NOT EXISTS brand (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    brand_name VARCHAR(40) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    id INTEGER PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS prices(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    brand_id INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    price_list INTEGER,
    product_id INTEGER,
    priority INTEGER,
    price DECIMAL(10,2),
    curr VARCHAR(10),
    FOREIGN KEY (brand_id) REFERENCES brand (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);