package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record PayerDTO(
    String nome,
    String cpf,
    String cnpj
){

    public String toString(){
        String out = "\"devedor\": {\"nome\": %s, \"cpf\": %s, \"cnpj\": %s}";
        return String.format(
            out, 
            this.nome, 
            this.cpf != null && !this.cpf.isEmpty() ? "\""+this.cpf+"\"" : "", 
            this.cnpj != null && !this.cnpj.isEmpty() ? "\""+this.cnpj+"\"" : ""
        );
    }

}
