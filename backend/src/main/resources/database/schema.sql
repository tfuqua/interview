DROP TABLE IF EXISTS USER_CARS;
 
CREATE TABLE USER_CARS (
id INT AUTO_INCREMENT  PRIMARY KEY,
    car_brand VARCHAR(250) NOT NULL,
    car_model VARCHAR(250) NOT NULL,
    car_color VARCHAR(250) NOT NULL
);