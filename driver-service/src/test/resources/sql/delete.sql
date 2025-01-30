DELETE FROM driver;
ALTER SEQUENCE driver_id_seq RESTART with 1;
DELETE FROM car;
ALTER SEQUENCE car_id_seq RESTART with 1;
