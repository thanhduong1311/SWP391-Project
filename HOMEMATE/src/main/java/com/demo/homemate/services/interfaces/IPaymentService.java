package com.demo.homemate.services.interfaces;

public interface IPaymentService {
    public double getTotalTime(String from, String to);

    public  double getTotalMoney(double hour,int serviceID);

    public boolean payBill(double amount);

    public long convertMoney(double amount);
}
