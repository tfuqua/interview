-- Provide SQL scripts here
CREATE TABLE PRODUCTS (
   id IDENTITY PRIMARY KEY,
   name VARCHAR(50) NOT NULL,
   description TEXT NOT NULL,
   quantity INT,
   rating DOUBLE
);