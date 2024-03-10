package com.example.wedding_gifts_api.common;

import java.time.ZoneId;
import java.time.ZoneOffset;

public final class MyZone {
    
    public static ZoneId zoneId() {
        return ZoneId.of("-03:00");
    }

    public static ZoneOffset zoneOffset() {
        return ZoneOffset.of("-03:00");
    }

}
