ALTER TABLE tb_payment RENAME payer_cpf TO payer_document;
ALTER TABLE tb_payment DROP COLUMN is_paid;