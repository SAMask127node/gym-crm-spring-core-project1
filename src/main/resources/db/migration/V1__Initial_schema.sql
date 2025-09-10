-- 1) Base user table
CREATE TABLE users (
                       id           BIGSERIAL      PRIMARY KEY,
                       first_name   VARCHAR(100)   NOT NULL,
                       last_name    VARCHAR(100)   NOT NULL,
                       username     VARCHAR(50)    NOT NULL UNIQUE,
                       password     VARCHAR(255)   NOT NULL,
                       is_active    BOOLEAN        NOT NULL DEFAULT TRUE
);

-- 2) Training types lookup table
CREATE TABLE training_type (
                               id           BIGSERIAL      PRIMARY KEY,
                               name         VARCHAR(255)   NOT NULL
);

-- 3) Trainee — one‑to‑one back to users
CREATE TABLE trainee (
                         id            BIGSERIAL      PRIMARY KEY,
                         date_of_birth DATE,
                         address       VARCHAR(255),
                         user_id       BIGINT         NOT NULL UNIQUE,
                         CONSTRAINT fk_trainee_user
                             FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4) Trainer — one‑to‑one back to users
CREATE TABLE trainer (
                         id             BIGSERIAL      PRIMARY KEY,
                         specialization VARCHAR(100),
                         user_id        BIGINT         NOT NULL UNIQUE,
                         CONSTRAINT fk_trainer_user
                             FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 5) Training sessions
CREATE TABLE training (
                          id                 BIGSERIAL      PRIMARY KEY,
                          trainee_id         BIGINT         NOT NULL,
                          trainer_id         BIGINT         NOT NULL,
                          training_name      VARCHAR(100)   NOT NULL,
                          training_type_id   BIGINT         NOT NULL,
                          training_date      DATE           NOT NULL,
                          training_duration  INT            NOT NULL,
                          CONSTRAINT fk_training_trainee
                              FOREIGN KEY(trainee_id) REFERENCES trainee(id),
                          CONSTRAINT fk_training_trainer
                              FOREIGN KEY(trainer_id) REFERENCES trainer(id),
                          CONSTRAINT fk_training_type
                              FOREIGN KEY(training_type_id) REFERENCES training_type(id)
);