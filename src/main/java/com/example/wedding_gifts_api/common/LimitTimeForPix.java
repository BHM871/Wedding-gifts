package com.example.wedding_gifts_api.common;

public final class LimitTimeForPix {

    public static Long getLimit() {
        int minute = 5;
        long seconds = minute * 60;

        return seconds;
    }
    
}
