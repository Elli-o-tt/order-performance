package com.order.application.model.order;

import lombok.Builder;

@Builder
public record OrderResult(Boolean isSuccess, Long orderId) {}
