CREATE TABLE refresh_tokens
(
    -- Primary Key
    id          BIGSERIAL       NOT NULL,

    -- Foreign Key
    user_id     UUID            NOT NULL,

    -- Token Data
    token       VARCHAR(500)    NOT NULL,
    expires_at  TIMESTAMP       NOT NULL,
    revoked     BOOLEAN         NOT NULL DEFAULT FALSE,

    -- Constraints
    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id),
    CONSTRAINT uq_refresh_tokens_token UNIQUE (token),
    CONSTRAINT fk_refresh_tokens_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

-- Index for token lookup: findByToken
CREATE INDEX idx_refresh_tokens_token ON refresh_tokens (token);

-- Index for revokeAllByUserId
CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens (user_id);

-- Index to quickly find valid tokens
CREATE INDEX idx_refresh_tokens_user_id_revoked ON refresh_tokens (user_id, revoked);