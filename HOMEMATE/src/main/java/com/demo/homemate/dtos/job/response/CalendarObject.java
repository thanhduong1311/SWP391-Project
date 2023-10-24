package com.demo.homemate.dtos.job.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CalendarObject {
    private String id;
    private String name;
    private String date;
    private String type;
    private String description;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"" +
                ", \"name\":\"" + name + "\"" +
                ", \"date\":\"" + date + "\"" +
                ", \"type\":\"" + type + "\"" +
                ", \"description\":\"" + description + "\"" +
                "}";
    }

    public String toCalen() {
        return "$(\"#evoCalendar\").evoCalendar('addCalendarEvent',\n" + this.toString() + ");";
    }
}
