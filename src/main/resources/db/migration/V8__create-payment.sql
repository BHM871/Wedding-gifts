CREATE TABLE tb_payment(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    payer TEXT NOT NULL,
    payerCpf TEXT NOT NULL,
    payment_value NUMERIC NOT NULL,
    creation TIMESTAMP NOT NULL,
    expiration TIMESTAMP NOT NULL,
    paid TIMESTAMP,
    is_paid BOOLEAN NOT NULL,
    payemnt_code TEXT UNIQUE,
    method CHARACTER VARYING NOT NULL,
    gift_id UUID NOT NULL UNIQUE,
    account_id UUID NOT NULL
);