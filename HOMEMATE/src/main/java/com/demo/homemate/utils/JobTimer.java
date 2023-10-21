package com.demo.homemate.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JobTimer {

    public Date getTimeStart(String from) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            Date fromDate = sdf.parse(from.split("T")[0]);

            int hourS = Integer.parseInt(from.split("T")[1].split(":")[0]);
            int minuteS = Integer.parseInt(from.split("T")[1].split(":")[1]);


            return  new Date(fromDate.getYear(),fromDate.getMonth(),fromDate.getDate(),hourS,minuteS,0);


        } catch (Exception e) {
            throw new RuntimeException("Error");
        }

    }

    public Date getTimeEnd(String to) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            Date toDate = sdf.parse(to.split("T")[0]);


            int hourE = Integer.parseInt(to.split("T")[1].split(":")[0]);
            int minuteE = Integer.parseInt(to.split("T")[1].split(":")[1]);

            return new Date(toDate.getYear(),toDate.getMonth(),toDate.getDate(),hourE,minuteE,0);

        } catch (Exception e) {
            throw new RuntimeException("Error");
        }

    }
}
