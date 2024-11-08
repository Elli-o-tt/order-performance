package com.order.presentation.model.order;

import com.order.application.model.order.OrderCommand;

public record OrderRequest(Long productId, Long memberId, Object paymentInfo) {

    public OrderCommand toCommand() {
        return OrderCommand.builder()
                .productId(this.productId)
                .memberId(this.memberId)
                .paymentInfo(this.paymentInfo)
                .build();
    }
}
