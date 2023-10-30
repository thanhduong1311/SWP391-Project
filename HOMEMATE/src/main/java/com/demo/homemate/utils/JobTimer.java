package com.demo.homemate.utils;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public boolean checkBusy(Date a, Date b, Date c) {
        // Lấy thời gian theo mili giây
        long aTime = a.getTime();
        long bTime = b.getTime();
        long cTime = c.getTime();

        // So sánh thời gian của `c` với thời gian của `A` và `B`
        return cTime >= aTime && cTime <= bTime;
    }

    public boolean checkDone(Date a, Date b) {
        // Lấy thời gian theo mili giây
        long aTime = a.getTime();
        long bTime = b.getTime();

        return bTime >= aTime;
    }

    public boolean checkValidCancel(Date a,Date b) {
        // Lấy thời gian theo mili giây
        long aTime = a.getTime();
        long bTime =b.getTime();

        long diff = bTime - aTime;

        // Kiểm tra xem khoảng thời gian nhỏ hơn 1 tiếng
        return diff > (1000 * 60 * 60);
    }

    public boolean isExpired(Date a) {
        // Lấy thời gian theo mili giây
        long aTime = a.getTime();
        long bTime = (new Date()).getTime(); // now

        return bTime > aTime;
    }


    public static String convertDateToString(Date date) {
        // Create a SimpleDateFormat object with the desired format.
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        // Use the SimpleDateFormat object's format() method to format the Date object.
        String formattedDate = formatter.format(date);

        // Return the formatted date string.
        return formattedDate;
    }

    public String toBirthDay(Date date) {
        String result = "";
        String splitDate = date.toString().trim();
        String s = "2000-02-01 00:00:00.0";
        String stringDate = date.toString().split(" ")[0];
        result += stringDate.split("-")[0]+"-";
        result += stringDate.split("-")[1]+"-";
        result += stringDate.split("-")[2];

        return result;
    }

    public  String toCalendarDate(Date date) {
        String result = "";
        String splitDate = date.toString().trim();
        String s = "2000-02-01 00:00:00.0";
        String stringDate = date.toString().split(" ")[0];
        result += stringDate.split("-")[1]+"/";
        result += stringDate.split("-")[2]+"/";
        result += stringDate.split("-")[1];


        return result;
    }


    @SneakyThrows
    public int checkAge(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (sdf.parse(dob));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        Calendar today = Calendar.getInstance();
        int todayYear = today.get(Calendar.YEAR);

        int age = todayYear - year;

        return age > 16 ? 1 : 0;
    }


    public Date getEndTime(String from, double hour) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date fromDate = sdf.parse(from);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fromDate);
            calendar.add(Calendar.HOUR, (int) Math.floor(hour));
            calendar.add(Calendar.MINUTE, (int) Math.round((hour - Math.floor(hour)) * 60));
            Date endTime = calendar.getTime();

            return endTime;
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }
    }


    public static void main(String[] args) {
        JobTimer j = new JobTimer();

        System.out.println(j.getEndTime("2023-10-29T11:46",1.5));
    }

}
