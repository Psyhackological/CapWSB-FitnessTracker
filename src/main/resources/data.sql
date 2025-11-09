-- Optional sample data to validate the model
-- Order matters: delete children first, then parents
DELETE FROM health_metrics;
DELETE FROM trainings;
DELETE FROM statistics;
DELETE FROM users;

-- Users (parent)
INSERT INTO users (id, first_name, last_name, birthday, email) VALUES
  (1, 'Alice', 'Runner', DATE '1990-05-15', 'alice@example.com'),
  (2, 'Bob',   'Walker', DATE '1985-08-21', 'bob@example.com');

-- Statistics (OneToOne to users; user_id must be unique)
INSERT INTO statistics (id, user_id, total_trainings, total_distance, total_calories_burned) VALUES
  (1, 1, 5, 42.50, 3000),
  (2, 2, 0,  0.00, 0);

-- Health metrics (ManyToOne to users)
INSERT INTO health_metrics (id, user_id, date, weight, height, heart_rate) VALUES
  (1, 1, DATE '2025-11-01', 68.50, 172, 60),
  (2, 1, DATE '2025-11-08', 68.20, 172, 58),
  (3, 2, DATE '2025-11-08', 82.30, 180, 65);

-- Trainings (ManyToOne to users)
-- NOTE: activity_type must match your enum constants in pl.wsb.fitnesstracker.training.internal.ActivityType
-- Common examples: RUNNING, CYCLING, WALKING (adjust if your enum uses different names)
INSERT INTO trainings (id, user_id, start_time, end_time, activity_type, distance, average_speed) VALUES
  (1, 1, TIMESTAMP '2025-11-08 07:30:00', TIMESTAMP '2025-11-08 08:15:00', 'RUNNING', 10.0, 13.3),
  (2, 1, TIMESTAMP '2025-11-09 18:00:00', TIMESTAMP '2025-11-09 18:45:00', 'CYCLING', 20.0, 26.6),
  (3, 2, TIMESTAMP '2025-11-08 09:00:00', TIMESTAMP '2025-11-08 09:25:00', 'WALKING',  3.2,  7.7);
