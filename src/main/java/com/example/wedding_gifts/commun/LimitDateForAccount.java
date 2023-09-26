package com.example.wedding_gifts.commun;

import java.time.Instant;
import java.util.Date;

public final class LimitDateForAccount {
    
    public static boolean deadlineHasNotPassed(Date weddingDate){
        int days = 7;
        int hours = days * 24;
        long minutes = hours * 60L; 
        long seconds = minutes * 60L;

        Instant now = new Date().toInstant();
        Instant limit = weddingDate.toInstant().plusSeconds(seconds);

        if(now.isAfter(limit)) return false;
        else return true;
    }

}
