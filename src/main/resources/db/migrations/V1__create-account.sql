CREATE TABLE tb_account(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    bride_groom TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    passwordd TEXT NOT NULL,
    pix_key TEXT NOT NULL UNIQUE
);