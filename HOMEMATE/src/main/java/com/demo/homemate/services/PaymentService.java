package com.demo.homemate.services;

import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IPaymentService;
import com.demo.homemate.utils.JobTimer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService implements IPaymentService {

    private final ServiceRepository serviceRepository;
    @SneakyThrows
    @Override
    public double getTotalTime(String from, String to) {

        try {


            JobTimer jobTimer = new JobTimer();

            Date StartTime = jobTimer.getTimeStart(from);
            Date EndTime = jobTimer.getTimeEnd(to);

            long servicetime = EndTime.getTime() - StartTime.getTime();

            return ((double)servicetime / (1000 * 60 * 60));
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }

    }

    @SneakyThrows
    @Override
    public double getTotalTime(Date from, Date to) {

        try {

            long servicetime = to.getTime() - from.getTime();
            double time = ((double)servicetime / (1000 * 60 * 60));
            BigDecimal bd = new BigDecimal(time);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            time = bd.doubleValue();

            return time;
        } catch (Exception e) {
            throw new RuntimeException("Error");
        }

    }

    @Override
    public double getTotalTime(Date from, double hour) {
        return 0;
    }


    @Override
    public double getTotalMoney(double hour, int serviceID) {
        double price = serviceRepository.findById(serviceID).getPrice();
        double totalMoney = hour * price;

        // Round the total money to 2 decimal places
        BigDecimal bd = new BigDecimal(totalMoney);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        totalMoney = bd.doubleValue();

        return totalMoney;
    }

    @Override
    public boolean payBill(double amount) {
        return false;
    }

    @Override
    public long convertMoney(double amount) {
        // Khai báo biến để lưu trữ tỷ giá chuyển đổi
        double exchangeRate = 24540.00000 ;

        // Tính toán số tiền VND tương đương với số tiền $ được cung cấp
        long amountVND = (long) (amount * exchangeRate);

        // Trả về số tiền VND đã tính toán
        return amountVND;
    }
}
