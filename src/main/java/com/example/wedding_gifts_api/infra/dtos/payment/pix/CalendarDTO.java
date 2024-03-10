package com.example.wedding_gifts_api.infra.dtos.payment.pix;

import java.time.LocalDateTime;

public record CalendarDTO(
    LocalDateTime criacao,
    Long expiracao
){

    public String toString(){
        String out = "\"calendario\": {\"expiracao\": %d";

        if(criacao != null) 
            out += ",\"criacao\": \"%s\"}";
        else 
            out += "}";

        return String.format(
            out, 
            this.expiracao, 
            this.criacao != null ? this.criacao.toString() : ""
        );
    }

}
