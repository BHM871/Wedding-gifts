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

        out += this.calendario.toString() + ",\n";
        out += this.devedor.toString() + ",\n";
        out += this.valor.toString() + ",\n";
        out += "\"chave\":" + this.chave + ",\n";
        out += "\"solicitacaoPagador\":" + this.solicitacaoPagador + "\n";

        out += "}";

        return out;
    }

}
