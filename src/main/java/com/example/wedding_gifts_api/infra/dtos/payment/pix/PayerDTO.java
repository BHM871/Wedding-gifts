package com.example.wedding_gifts_api.infra.dtos.payment.pix;

public record PayerDTO(
    String nome,
    String cpf,
    String cnpj
){

    public String toString(){
        String out = "\"devedor\": {\"nome\": \"%s\"";

        if(cpf != null) 
            out += ", \"cpf\": \"%s\"}";
        else 
            out += ", \"cnpj\": \"%s\"}";

        return String.format(
            out, 
            this.nome, 
            this.cpf != null && !this.cpf.isEmpty() ? this.cpf : this.cnpj
        );
    }

}
