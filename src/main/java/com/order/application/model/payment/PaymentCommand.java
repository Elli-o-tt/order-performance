package com.order.application.model.payment;

import lombok.Builder;

@Builder
public record PaymentCommand(Integer price, Long memberId) {

    public void validate() {}

    public static PaymentCommand of(Integer price, Long memberId) {
        return PaymentCommand.builder().price(price).memberId(memberId).build();
    }
}
