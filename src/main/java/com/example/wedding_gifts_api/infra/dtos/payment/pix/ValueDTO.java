package com.example.wedding_gifts_api.infra.dtos.payment.pix;

import java.math.BigDecimal;

public record ValueDTO(
    String original,
    BigDecimal value,
    int modalidadeAlteracao
){

    public String toString(){
        String out = "\"valor\": {\"original\": \"%.2f\"}";
        return String.format(
            out,
            this.value
        );
    }

}
