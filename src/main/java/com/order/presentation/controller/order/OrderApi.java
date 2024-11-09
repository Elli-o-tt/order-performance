package com.order.presentation.controller.order;

import com.order.application.model.order.OrderResult;
import com.order.application.service.order.AsyncOrderService;
import com.order.application.service.order.SyncOrderService;
import com.order.presentation.model.order.OrderRequest;
import com.order.presentation.model.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderApi {

    private final SyncOrderService syncOrderService;
    private final AsyncOrderService asyncOrderService;

    @PostMapping("/syncOrder")
    public OrderResponse syncOrder(@RequestBody OrderRequest orderRequest) {

        OrderResponse response;
        try {
            OrderResult result = syncOrderService.order(orderRequest.toCommand());
            response = OrderResponse.success(result);
        } catch (RuntimeException e) {
            response = OrderResponse.failed(e.getMessage());
        }
        return response;
    }

    @PostMapping("/asyncOrder")
    public OrderResponse asyncOrder(@RequestBody OrderRequest orderRequest) {

        OrderResponse response;
        try {
            OrderResult result = asyncOrderService.order(orderRequest.toCommand());
            response = OrderResponse.success(result);
        } catch (RuntimeException e) {
            response = OrderResponse.failed(e.getMessage());
        }
        return response;
    }
}
