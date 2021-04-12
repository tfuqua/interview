-- Provide SQL scripts here
CREATE TABLE CAR_SHOP
(
    id                 IDENTITY PRIMARY KEY,
    name               VARCHAR(60) NOT NULL UNIQUE,
    description        VARCHAR(500),
    summary            VARCHAR(120),
    logo_url           TEXT,
    created_by         VARCHAR(60) NOT NULL,
    created_date       DATE        NOT NULL,
    last_modified_by   VARCHAR(60) NOT NULL,
    last_modified_date DATE        NOT NULL,
    version            INT DEFAULT 0
);