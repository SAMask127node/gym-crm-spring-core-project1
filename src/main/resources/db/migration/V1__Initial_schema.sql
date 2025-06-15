CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE trainee (
                         id BIGINT PRIMARY KEY,
                         date_of_birth DATE NOT NULL,
                         CONSTRAINT fk_trainee_user FOREIGN KEY (id)
                             REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE trainer (
                         id BIGINT PRIMARY KEY,
                         CONSTRAINT fk_trainer_user FOREIGN KEY (id)
                             REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE training_type (
                               code VARCHAR(20) PRIMARY KEY,
                               description VARCHAR(255)
);

CREATE TABLE training (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          date DATE NOT NULL,
                          duration INT NOT NULL,
                          trainee_id BIGINT NOT NULL,
                          trainer_id BIGINT NOT NULL,
                          type_code VARCHAR(20) NOT NULL,
                          CONSTRAINT fk_training_trainee FOREIGN KEY (trainee_id)
                              REFERENCES trainee(id),
                          CONSTRAINT fk_training_trainer FOREIGN KEY (trainer_id)
                              REFERENCES trainer(id),
                          CONSTRAINT fk_training_type FOREIGN KEY (type_code)
                              REFERENCES training_type(code)
);
