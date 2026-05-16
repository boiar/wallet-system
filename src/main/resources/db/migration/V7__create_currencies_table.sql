CREATE TABLE currencies
(
    id           BIGSERIAL      NOT NULL,
    code         VARCHAR(10)    NOT NULL,
    symbol       VARCHAR(10)    NOT NULL,
    name         JSONB          NOT NULL,
    is_active    BOOLEAN        NOT NULL DEFAULT TRUE,

    CONSTRAINT pk_currencies PRIMARY KEY (id),
    CONSTRAINT uq_currencies_code UNIQUE (code),
    CONSTRAINT uq_currencies_symbol UNIQUE (symbol)
);

