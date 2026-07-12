CREATE TABLE users (
                       id            UUID PRIMARY KEY,
                       email         VARCHAR(150) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role          VARCHAR(20)  NOT NULL,
                       created_at    TIMESTAMPTZ  NOT NULL
);

CREATE TABLE categories (
                            id       UUID PRIMARY KEY,
                            user_id  UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                            name     VARCHAR(100) NOT NULL,
                            color    VARCHAR(20),
                            UNIQUE (user_id, name)
);

CREATE TABLE expenses (
                          id           UUID PRIMARY KEY,
                          user_id      UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          category_id  UUID REFERENCES categories(id) ON DELETE SET NULL,
                          amount_kes   NUMERIC(12,2) NOT NULL CHECK (amount_kes >= 0),
                          description  VARCHAR(255),
                          occurred_on  DATE NOT NULL,
                          created_at   TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_expenses_user_date ON expenses(user_id, occurred_on);