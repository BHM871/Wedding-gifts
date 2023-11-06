package com.example.wedding_gifts.core.domain.dtos.payment.pix;

import java.time.LocalDateTime;

public record CalendarDTO(
    LocalDateTime criacao,
    Long expiracao
){

    public String toString(){
        return "\"calendario\": {\"expiracao\": " + this.expiracao + ", \"criacao\": " + this.criacao + "}";
    }

}
