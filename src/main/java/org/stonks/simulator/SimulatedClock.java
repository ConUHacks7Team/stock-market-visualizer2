package org.stonks.simulator;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SimulatedClock{
    static public DateTimeFormatter dtf_stock = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");
    public SimulatedClock(){}
    public Clock clock;
    public Clock generateClock(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime wantedStartTime = LocalDateTime.parse("2023-01-06 09:30:00.000000000", dtf_stock);

        Duration time_diff = Duration.between(currentTime, wantedStartTime);

        Clock currentSysClock = Clock.system(ZoneId.of("America/Toronto"));
        this.clock = Clock.offset(currentSysClock, time_diff);

        LocalDateTime clockToLocalDateTime = LocalDateTime.ofInstant(this.clock.instant(),ZoneId.of("America/Toronto"));
//        System.out.println("CLOCK TO LOCALDATETIME " + clockToLocalDateTime);

//        System.out.println("Result " + this.clock.instant().toString());
        return this.clock;
    }

    static public LocalDateTime clockToLocalDateTime(Clock offsetedClock){
        LocalDateTime clockToLocalDateTime = LocalDateTime.ofInstant(offsetedClock.instant(),ZoneId.of("America/Toronto"));
        return clockToLocalDateTime;
    }

    static public LocalDateTime timeStampToLocalDateTime(String timeStamp){
        LocalDateTime time = LocalDateTime.parse(timeStamp, dtf_stock);
        return time;
    }

}