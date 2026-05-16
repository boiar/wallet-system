CREATE TABLE notifications
(
    id            BIGSERIAL       NOT NULL,
    user_id       UUID            NOT NULL,
    title         VARCHAR(255)    NOT NULL,
    body          TEXT            NOT NULL,
    event         VARCHAR(100)    NOT NULL,
    type          VARCHAR(50)     NOT NULL,     -- EMAIL | REALTIME
    status        VARCHAR(50)     NOT NULL  DEFAULT 'PENDING', -- PENDING | SENT | FAILED | READ

    metadata      JSONB,                        -- extra data (otp, amount, etc.)
    failure_reason VARCHAR(500),                -- why it failed
    read_at        TIMESTAMP,
    sent_at        TIMESTAMP,

    created_date  TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_date  TIMESTAMP       NOT NULL DEFAULT NOW(),
    deleted_date  TIMESTAMP       NULL,

    CONSTRAINT pk_notifications PRIMARY KEY (id),
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT chk_notifications_type CHECK (type IN ('EMAIL', 'REALTIME')),
    CONSTRAINT chk_notifications_status CHECK (status IN ('PENDING', 'SENT', 'FAILED', 'READ'))
);

CREATE INDEX idx_notifications_user_id       ON notifications (user_id);
CREATE INDEX idx_notifications_status        ON notifications (status);
CREATE INDEX idx_notifications_type          ON notifications (type);
CREATE INDEX idx_notifications_user_status   ON notifications (user_id, status);
CREATE INDEX idx_notifications_created_date  ON notifications (created_date DESC);