ALTER TABLE tb_oauth_psb ADD scope CHARACTER VARYING(255);
ALTER TABLE tb_payment RENAME payemnt_code TO payment_code;
ALTER TABLE tb_payment RENAME payercpf TO payer_cpf;
ALTER TABLE tb_payment ALTER COLUMN method TYPE character varying(255);
ALTER TABLE tb_payment ADD COLUMN payment_status character varying(255) NOT NULL;