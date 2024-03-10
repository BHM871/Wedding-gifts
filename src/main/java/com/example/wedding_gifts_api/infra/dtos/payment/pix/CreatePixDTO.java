package com.example.wedding_gifts_api.infra.dtos.payment.pix;

public record CreatePixDTO(
    CalendarDTO calendario,
    PayerDTO devedor,
    ValueDTO valor,
    String chave,
    String solicitacaoPagador
){

    public String toString(){
        String out = "{%s, %s, %s, \"chave\": \"%s\", \"solicitacaoPagador\": \"%s\"}";
        return String.format(
            out, 
            this.calendario.toString(),
            this.devedor.toString(),
            this.valor.toString(),
            chave,
            this.solicitacaoPagador
        );
    }

}
