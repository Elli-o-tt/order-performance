package com.order.presentation.model.order;

import com.order.application.model.order.OrderCommand;

public record OrderRequest(Long productId, Long memberId) {

    public OrderCommand toCommand() {
        return OrderCommand.builder().productId(this.productId).memberId(this.memberId).build();
    }
}
