CREATE TABLE otps
(
    -- Primary Key
    id          BIGSERIAL       NOT NULL,

    -- Foreign Key
    user_id     UUID            NOT NULL,

    -- OTP Data
    code        VARCHAR(10)     NOT NULL,
    otp_type    VARCHAR(50)     NOT NULL,   -- REGISTER | FORGET_PASSWORD
    expires_at  TIMESTAMP       NOT NULL,
    used        BOOLEAN         NOT NULL DEFAULT FALSE,

    -- Constraints
    CONSTRAINT pk_otps PRIMARY KEY (id),
    CONSTRAINT fk_otps_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT chk_otps_type
        CHECK (otp_type IN ('REGISTER', 'FORGET_PASSWORD'))
);

-- Index to speed up lookup: findTopByUserEmailAndTypeAndUsedFalse
CREATE INDEX idx_otps_user_id_type_used ON otps (user_id, otp_type, used);

CREATE INDEX idx_otps_expires_at ON otps (expires_at);