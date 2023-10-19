package com.example.wedding_gifts.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class LimitTimeForToken {

    public static Instant genExpirationInstant(){
        return LocalDateTime.now().plusHours(1).plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }
    
}
