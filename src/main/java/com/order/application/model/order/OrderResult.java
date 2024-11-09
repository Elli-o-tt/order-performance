package com.order.application.model.order;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OrderResult(Long orderId) {

    public static OrderResult of(Long orderId) {
        return OrderResult.builder().orderId(orderId).build();
    }
}
