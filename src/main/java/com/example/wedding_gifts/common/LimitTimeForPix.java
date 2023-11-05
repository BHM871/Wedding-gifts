package com.example.wedding_gifts.common;

public final class LimitTimeForPix {

    public static Long getLimit() {
        int minute = 5;
        long seconds = minute * 60;

        return seconds;
    }
    
}
