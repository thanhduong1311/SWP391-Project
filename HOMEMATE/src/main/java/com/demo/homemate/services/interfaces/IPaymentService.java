package com.demo.homemate.services.interfaces;

import java.util.Date;

public interface IPaymentService {
    public double getDiscountedFinalMoney(double hour, int serviceID,int rankID);
    public double getTotalTime(String from, String to);

    public  double getTotalMoney(double hour,int serviceID);

    public  double getDiscount(double totalPrice,int rankID, int serviceID);

    public boolean payBill(double amount);

    public long convertMoney(double amount);

    public double getTotalTime(Date from, Date to);
    public double getTotalTime(Date from, double hour);
}
