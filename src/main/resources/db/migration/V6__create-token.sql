CREATE TABLE tb_token(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    token_value TEXT NOT NULL UNIQUE,
    limit_hour TIMESTAMP NOT NULL,
    account_id UUID NOT NULL UNIQUE,
    CONSTRAINT fk_token_account FOREIGN KEY(account_id) REFERENCES tb_account(id)
);