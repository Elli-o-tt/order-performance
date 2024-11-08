package com.order.application.model.order;

import lombok.Builder;

@Builder
public record OrderCommand(Long productId, Long memberId, Object paymentInfo) {

    public void validate() {}
}
