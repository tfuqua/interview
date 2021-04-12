-- Provide SQL scripts here
CREATE TABLE CARS (
   id IDENTITY PRIMARY KEY,
   model VARCHAR(20) NOT NULL,
   price DOUBLE
);

INSERT INTO CARS (id, model, price) VALUES (1, 'BMW', 145);
INSERT INTO CARS (id, model, price) VALUES (2, 'Audi', 387.6);
INSERT INTO CARS (id, model, price) VALUES (3, 'Toyota', 974);