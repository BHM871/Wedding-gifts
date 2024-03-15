package com.example.wedding_gifts_api.core.domain.model.util;

public enum PaymentStatus {
    ATCTIVE,
    COMPLETE;

    public static PaymentStatus byPixStatus(String pixStatus){
        if(pixStatus.equals("CONLUIDA")){
            return PaymentStatus.COMPLETE;
        }

        return PaymentStatus.ATCTIVE;
    }
}