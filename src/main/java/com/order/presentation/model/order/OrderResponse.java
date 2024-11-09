package com.order.presentation.model.order;

import com.order.application.model.order.OrderResult;
import lombok.Builder;

@Builder
public record OrderResponse(Boolean isSuccess, String message, Long orderId) {
    public static OrderResponse success(OrderResult result) {
        return OrderResponse.builder().isSuccess(Boolean.TRUE).orderId(result.orderId()).build();
    }

    public static OrderResponse failed(String message) {
        return OrderResponse.builder().isSuccess(Boolean.FALSE).message(message).build();
    }
}
