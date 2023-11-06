package com.example.wedding_gifts.core.domain.dtos.payment.pix;

public record ValueDTO(
    String original
){

    public String toString(){
        return "\"valor\": {\"original\": " + this.original + "}";
    }

}
