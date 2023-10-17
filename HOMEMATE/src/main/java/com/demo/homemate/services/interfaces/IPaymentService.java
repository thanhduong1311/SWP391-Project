package com.demo.homemate.services.interfaces;

public interface IPaymentService {
    public double getTotalTime(String from, String to);

    public  double getTotalMoney(double hour, double price);

    public boolean payBill(double amount);
}
