package com.example.wedding_gifts.common;

import java.time.ZoneId;
import java.time.ZoneOffset;

public final class MyZoneOffSet {
    
    public static ZoneId zoneId() {
        return ZoneId.of("-03:00");
    }

    public static ZoneOffset zoneOffset() {
        return ZoneOffset.of("-03:00");
    }

}
