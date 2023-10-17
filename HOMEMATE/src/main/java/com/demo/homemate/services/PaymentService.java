package com.demo.homemate.services;

import com.demo.homemate.services.interfaces.IPaymentService;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentService implements IPaymentService {
    @SneakyThrows
    @Override
    public double getTotalTime(String from, String to) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            Date fromDate = sdf.parse(from.split("T")[0]);
            Date toDate = sdf.parse(to.split("T")[0]);

            int hourS = Integer.parseInt(from.split("T")[1].split(":")[0]);
            int minuteS = Integer.parseInt(from.split("T")[1].split(":")[1]);

            int hourE = Integer.parseInt(to.split("T")[1].split(":")[0]);
            int minuteE = Integer.parseInt(to.split("T")[1].split(":")[1]);

            Date StartTime = new Date(fromDate.getYear(),fromDate.getMonth(),fromDate.getDate(),hourS,minuteS,0);
            Date EndTime = new Date(toDate.getYear(),toDate.getMonth(),toDate.getDate(),hourE,minuteE,0);

            long servicetime = EndTime.getTime() - StartTime.getTime();

            return ((double)servicetime / (1000 * 60 * 60));
        } catch (Exception e) {
            return -1;
        }

    }

    @Override
    public double getTotalMoney(double hour, double price) {
        return hour * price;
    }

    @Override
    public boolean payBill(double amount) {



        return false;
    }
}
