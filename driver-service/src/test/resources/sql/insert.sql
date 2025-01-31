INSERT INTO car (license_number, color, seats, brand, model, car_category, created_at)
VALUES
    ('ABC123', 'Black', 4, 'Toyota', 'Camry', 'ECONOMY', NOW()),
    ('XYZ789', 'White', 4, 'Honda', 'Civic', 'ECONOMY', NOW());

INSERT INTO driver (customer_id, car_id, first_name, last_name, phone, status, created_at)
VALUES
    ('cus_12345', null, 'Viktor', 'Maksimov', '+375291234567', 'AVAILABLE', NOW()),
    ('cus_31541', null, 'Jane', 'Smith', '+375295234861', 'AVAILABLE', NOW());
