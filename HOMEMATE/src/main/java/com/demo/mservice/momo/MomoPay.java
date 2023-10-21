/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.demo.mservice.momo;


import com.demo.homemate.dtos.job.request.JobRequest;
import com.demo.mservice.config.Environment;
import com.demo.mservice.enums.RequestType;
import com.demo.mservice.models.PaymentResponse;
import com.demo.mservice.shared.utils.LogUtils;
import jakarta.servlet.http.HttpServletRequest;
import com.demo.mservice.processor.CreateOrderMoMo;
import jakarta.servlet.http.HttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author TTNhan
 */
public class MomoPay {

    public static String getPayLink(HttpServletRequest request, RequestType requestType, long amount, int customerID) {

        String payLink = null;

        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;

        String partnerClientId = "partnerClientId";

        //description pay
        String orderInfo = "Pay With MoMo";


        //link after pay success
        String returnURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/customer/bookings/completeBooking/" +customerID ;




        String notifyURL = "https://google.com.vn";
        String callbackToken = "callbackToken";
        String token = "";

        Environment environment = Environment.selectEnv("dev");

        try {
            if (requestType == RequestType.CAPTURE_WALLET) {

                PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
                payLink = captureWalletMoMoResponse.getPayUrl();
            } else {
                orderId = String.valueOf(System.currentTimeMillis());
                requestId = String.valueOf(System.currentTimeMillis());
                PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
                payLink = captureATMMoMoResponse.getPayUrl();
            }
        } catch (Exception ex) {
            Logger.getLogger(MomoPay.class.getName()).log(Level.SEVERE, null, ex);
        }

        return payLink;
    }
}
