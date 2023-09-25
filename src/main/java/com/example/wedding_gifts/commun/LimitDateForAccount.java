package com.example.wedding_gifts.commun;

import java.util.Date;

public final class LimitDateForAccount {
    
    public static boolean deadlineHasNotPassed(Date weddingDate){
        int day = 7;
        long seconds = day * 24 * 60 * 60;

        if(new Date().toInstant().isBefore(weddingDate.toInstant().plusSeconds(seconds))) return true;
        else return false;
    }

}
