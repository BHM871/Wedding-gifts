CREATE TABLE tb_change_request (
    id UUID NOT NULL UNIQUE,
    request_type TEXT NOT NULL,
    request TIMESTAMP NOT NULL,
    limit_hour TIMESTAMP NOT NULL,
    account_id UUID NOT NULL UNIQUE,
    CONSTRAINT fk_change_account 
        FOREIGN KEY(account_id) 
        REFERENCES tb_account(id)
);