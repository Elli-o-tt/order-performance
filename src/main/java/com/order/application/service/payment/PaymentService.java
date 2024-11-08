package com.order.application.service.payment;

import com.order.application.model.payment.PaymentCommand;
import com.order.application.model.payment.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public PaymentResult pay(PaymentCommand paymentCommand) {
        paymentCommand.validate();

        try {
            // 0.5초 동안 대기
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // 스레드가 인터럽트될 때 발생하는 예외 처리
            e.printStackTrace();
        }

        return PaymentResult.success();
    }
}
