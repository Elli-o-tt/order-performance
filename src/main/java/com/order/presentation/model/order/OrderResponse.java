package com.order.presentation.model.order;

import com.order.application.model.order.OrderResult;
import lombok.Builder;

@Builder
public record OrderResponse(Boolean isSuccess, Long orderId) {
    public static OrderResponse from(OrderResult result) {
        return OrderResponse.builder()
                .isSuccess(result.isSuccess())
                .orderId(result.orderId())
                .build();
    }
}
