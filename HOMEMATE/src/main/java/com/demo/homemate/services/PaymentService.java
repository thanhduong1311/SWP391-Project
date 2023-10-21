package com.demo.homemate.services;

import com.demo.homemate.repositories.ServiceRepository;
import com.demo.homemate.services.interfaces.IPaymentService;
import com.demo.homemate.utils.JobTimer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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





    @Override
    public double getTotalMoney(double hour, int serviceID) {
            double pirce = serviceRepository.findById(serviceID).getPrice();
            return hour * pirce;
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
