package com.order.application.model.payment;

import com.order.domain.Product;
import lombok.Builder;

@Builder
public record PaymentCommand(Integer price, Long memberId, Object paymentInfo) {

    public void validate() {}

    public static PaymentCommand of(Product product, Long memberId, Object paymentInfo) {
        return PaymentCommand.builder()
                .price(product.getPrice())
                .memberId(memberId)
                .paymentInfo(paymentInfo)
                .build();
    }
}
