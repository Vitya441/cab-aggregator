INSERT INTO ride (passenger_id, driver_id, pickup_address, destination_address, estimated_cost, actual_cost, status, rated_by_passenger, rated_by_driver, payment_method, start_time, end_time, created_at, updated_at)
VALUES
    (101, 202, '123 Main St', '456 Elm St', 25.50, 25.50, 'IN_PROGRESS', false, false, 'CREDIT_CARD', '2024-02-01T10:30:00', '2024-02-01T11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (102, 203, '789 Oak St', '101 Pine St', 30.00, 30.00, 'REQUESTED', true, true, 'CREDIT_CARD', '2024-02-02T12:00:00', '2024-02-02T12:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (103, 204, '456 Maple St', '202 Birch St', 20.00, 20.00, 'ACCEPTED', false, true, 'CREDIT_CARD', '2024-02-03T14:00:00', '2024-02-03T14:30:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 203, '123 Main St', '456 Elm St', 25.50, 25.50, 'COMPLETED', false, false, 'CREDIT_CARD', '2024-02-01T10:30:00', '2024-02-01T11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (101, 204, '123 Main St', '456 Elm St', 25.50, 25.50, 'COMPLETED', false, false, 'CREDIT_CARD', '2024-02-01T10:30:00', '2024-02-01T11:00:00', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

