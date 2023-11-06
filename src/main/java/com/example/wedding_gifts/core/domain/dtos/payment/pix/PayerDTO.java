package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record PayerDTO(
    String nome,
    String cpf,
    String cnpj
){

    public String toString(){
        String out = "\"devedor\": {\"nome\": " + this.nome + ", ";

        out += this.cpf != null 
            ? "\"cpf\": " + this.cpf + "}"
            : "\"cnpj\"?: " + this.cnpj + "}";

        return out;
    }

}
