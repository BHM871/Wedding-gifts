CREATE TABLE tb_image(
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    path_image TEXT NOT NULL UNIQUE,
    gift_id UUID NOT NULL,
    CONSTRAINT fk_image_gift FOREIGN KEY(gift_id) REFERENCES tb_gift(id)
);