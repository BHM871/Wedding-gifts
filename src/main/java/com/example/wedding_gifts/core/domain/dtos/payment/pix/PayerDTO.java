package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record PayerDTO(
    String nome,
    String cpf,
    String cnjp
){

    public String toString(){
        String out = "\"devedor\": {\"nome\":" + this.nome + ",";
        
        out += cpf != null 
            ? "\"cpf\":" + this.cpf 
            : "\"cnpj\":"+ this.cnjp;
            
        out += "}";

        return out;
    }

}
