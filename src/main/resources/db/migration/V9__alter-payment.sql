ALTER TABLE tb_payment ADD transaction_id TEXT NOT NULL UNIQUE;
ALTER TABLE tb_payment ADD CONSTRAINT fk_payment_gift FOREIGN KEY(gift_id) REFERENCES tb_gift(id);
ALTER TABLE tb_payment ADD CONSTRAINT fk_payment_account FOREIGN KEY(account_id) REFERENCES tb_account(id);