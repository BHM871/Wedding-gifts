package com.example.wedding_gifts_api.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class LimitTimeForToken {

    public static Instant genExpirationInstant(){
        return LocalDateTime.now(MyZone.zoneId()).plusHours(1).plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }
    
}
