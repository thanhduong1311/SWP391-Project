package com.demo.mservice;


import com.demo.mservice.config.Environment;
import com.demo.mservice.enums.RequestType;
import com.demo.mservice.models.PaymentResponse;
import com.demo.mservice.processor.CreateOrderMoMo;
import com.demo.mservice.shared.utils.LogUtils;

public class AllInOne {

    /***
     * Select environment
     * You can load config from file
     * MoMo only provide once endpoint for each envs: dev and prod
     * @param args
     * @throws
     */

    public static void main(String... args) throws Exception {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;
        long amount = 50000;

        String partnerClientId = "partnerClientId";
        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";
        String callbackToken = "callbackToken";
        String token = "";

        Environment environment = Environment.selectEnv("dev");


//      Remember to change the IDs at enviroment.properties file

        /***
         * create payment with capture momo wallet
         */
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        System.out.println(captureWalletMoMoResponse.getPayUrl());
        
        /***
         * create payment with Momo's ATM type
         */

        orderId = String.valueOf(System.currentTimeMillis());
        requestId = String.valueOf(System.currentTimeMillis());
        PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM, null);
        System.out.println(captureATMMoMoResponse.getPayUrl());
    }

}
