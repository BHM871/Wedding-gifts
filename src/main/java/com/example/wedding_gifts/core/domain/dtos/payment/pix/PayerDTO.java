package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record PayerDTO(
    String nome,
    String cpf
){

    public String toString(){
        return "\"devedor\": {\"nome\": " + this.nome + ", \"cpf\": " + this.cpf + "}";
    }

}
