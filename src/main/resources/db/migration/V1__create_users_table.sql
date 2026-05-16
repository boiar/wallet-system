CREATE TABLE users
(
    -- Primary Key
    id                  UUID            NOT NULL DEFAULT gen_random_uuid(),

    -- Personal Info
    first_name          VARCHAR(100)    NOT NULL,
    last_name           VARCHAR(100)    NOT NULL,
    email               VARCHAR(255)    NOT NULL,
    phone               VARCHAR(20)     NOT NULL,
    password            VARCHAR(255)    NOT NULL,

    -- Status
    is_enabled          BOOLEAN         NOT NULL DEFAULT FALSE,
    is_email_verified   BOOLEAN         NOT NULL DEFAULT FALSE,

    -- Profile
    profile_picture_url VARCHAR(500),

    -- Audit (EntityAuditTimingData @Embedded)
    created_date        TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_date        TIMESTAMP       NOT NULL DEFAULT NOW(),
    deleted_date        TIMESTAMP       NULL,

    -- Constraints
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_phone UNIQUE (phone)
);

-- Indexes for frequent lookups
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_phone ON users (phone);
CREATE INDEX idx_users_is_enabled ON users (is_enabled);