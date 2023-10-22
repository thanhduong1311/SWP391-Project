package com.demo.homemate.services.interfaces;

import java.util.Date;

public interface IPaymentService {
    public double getTotalTime(String from, String to);

    public  double getTotalMoney(double hour,int serviceID);

    public boolean payBill(double amount);

    public long convertMoney(double amount);

    public double getTotalTime(Date from, Date to);
}
