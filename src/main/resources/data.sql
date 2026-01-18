INSERT INTO users (
    id,
    first_name,
    last_name,
    email,
    birthdate
) VALUES (
    1,
    'Jan',
    'Kowalski',
    'jan.kowalski@test.com',
    DATE '1995-05-10'
);

INSERT INTO trainings (
    activity_type,
    distance,
    average_speed,
    start_time,
    end_time,
    user_id
) VALUES (
    0,
    5.0,
    10.0,
    TIMESTAMP '2026-01-10 10:00:00',
    TIMESTAMP '2026-01-10 11:00:00',
    1
);

