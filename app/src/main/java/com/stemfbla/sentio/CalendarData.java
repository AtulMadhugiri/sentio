package com.stemfbla.sentio;

public class CalendarData {
    public String clubName;
    public String eventName;
    public hirondelle.date4j.DateTime dateTime;
    public CalendarData(String eventName, String clubName, hirondelle.date4j.DateTime dateTime) {
        super();
        this.clubName = clubName;
        this.eventName = eventName;
        this.dateTime = dateTime;
    }
}
