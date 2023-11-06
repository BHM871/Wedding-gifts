package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record CreatePixDTO(
    CalendarDTO calendario,
    PayerDTO devedor,
    ValueDTO valor,
    String chave,
    String solicitacaoPagador
){

    public String toString(){
        String out = "{";

        out += this.calendario.toString() + ", ";
        out += this.devedor.toString() + ", ";
        out += this.valor.toString() + ", ";
        out += "\"chave\":" + this.chave + ", ";
        out += "\"solicitacaoPagador\":" + this.solicitacaoPagador;

        out += "}";

        return out;
    }

}
