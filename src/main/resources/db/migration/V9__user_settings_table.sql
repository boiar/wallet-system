CREATE TABLE user_settings
(
    id                  BIGSERIAL      NOT NULL,
    user_id             UUID         NOT NULL,

    default_language_id BIGSERIAL          NOT NULL,
    default_currency_id BIGSERIAL          NOT NULL,

    email_notifications BOOLEAN      NOT NULL DEFAULT TRUE,
    push_notifications  BOOLEAN      NOT NULL DEFAULT TRUE,

    created_date        TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_date        TIMESTAMP    NOT NULL DEFAULT NOW(),
    deleted_date        TIMESTAMP    NULL,

    CONSTRAINT pk_user_settings PRIMARY KEY (id),
    CONSTRAINT uq_user_settings_user_id UNIQUE (user_id),
    CONSTRAINT fk_user_settings_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_settings_language
        FOREIGN KEY (default_language_id) REFERENCES languages (id),
    CONSTRAINT fk_user_settings_currency
        FOREIGN KEY (default_currency_id) REFERENCES currencies (id)
);


