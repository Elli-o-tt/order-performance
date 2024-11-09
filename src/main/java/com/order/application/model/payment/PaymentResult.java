package com.order.application.model.payment;

import lombok.Builder;

@Builder
public record PaymentResult(Boolean isSuccess) {

    public static PaymentResult success() {
        return PaymentResult.builder().isSuccess(true).build();
    }

    public static PaymentResult fail() {
        return PaymentResult.builder().isSuccess(false).build();
    }
}
