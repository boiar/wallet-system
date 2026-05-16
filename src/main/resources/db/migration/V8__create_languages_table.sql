CREATE TABLE languages
(
    id            BIGSERIAL       NOT NULL,
    code          VARCHAR(10)     NOT NULL,
    name          JSONB           NOT NULL,
    is_active     BOOLEAN         NOT NULL DEFAULT TRUE,
    is_rtl        BOOLEAN         NOT NULL DEFAULT FALSE,
    created_date  TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_date  TIMESTAMP       NOT NULL DEFAULT NOW(),
    deleted_date  TIMESTAMP       NULL,

    CONSTRAINT pk_languages PRIMARY KEY (id),
    CONSTRAINT uq_languages_code UNIQUE (code)
);

