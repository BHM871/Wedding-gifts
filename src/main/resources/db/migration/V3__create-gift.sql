CREATE TYPE categories_enum AS ENUM('KITCHEN', 'ROOM', 'BEDROOM', 'TOILET');

CREATE TABLE tb_gift(
    id UUID PRIMARY KEY UNIQUE NOT NULL,
    title TEXT NOT NULL,
    gift_description TEXT,
    categories categories_enum[] NOT NULL,
    price NUMERIC NOT NULL,
    is_bought BOOLEAN,
    account_id UUID NOT NULL,
    CONSTRAINT fk_account_gift FOREIGN KEY(account_id) REFERENCES tb_account(id) 
);