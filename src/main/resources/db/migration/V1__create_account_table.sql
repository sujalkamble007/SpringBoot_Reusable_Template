CREATE TABLE account (
    id          BIGSERIAL PRIMARY KEY,
    holder_name VARCHAR(100)  NOT NULL,
    balance     NUMERIC(19,2) NOT NULL DEFAULT 0,
    currency    VARCHAR(3)    NOT NULL,
    version     BIGINT        NOT NULL DEFAULT 0,
    created_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ   NOT NULL DEFAULT now(),
    CONSTRAINT chk_account_balance_non_negative CHECK (balance >= 0)
);