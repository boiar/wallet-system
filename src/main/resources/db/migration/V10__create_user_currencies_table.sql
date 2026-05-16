CREATE TABLE user_currencies
(
    id           BIGSERIAL   NOT NULL,
    user_id      UUID        NOT NULL,
    currency_id  BIGSERIAL   NOT NULL,
    is_primary   BOOLEAN     NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT pk_user_currencies PRIMARY KEY (id),
    CONSTRAINT uq_user_currencies_user_currency UNIQUE (user_id, currency_id),
    CONSTRAINT fk_user_currencies_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_currencies_currency
        FOREIGN KEY (currency_id) REFERENCES currencies (id)
);


CREATE INDEX idx_user_currencies_user_id ON user_currencies (user_id);
CREATE INDEX idx_user_currencies_is_primary ON user_currencies (user_id, is_primary);