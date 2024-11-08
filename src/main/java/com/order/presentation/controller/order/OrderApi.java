package com.order.presentation.controller.order;

import com.order.application.model.order.OrderResult;
import com.order.application.service.order.StockCountInDBOrderService;
import com.order.presentation.model.order.OrderRequest;
import com.order.presentation.model.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderApi {

    private final StockCountInDBOrderService stockCountInDBOrderService;

    @PostMapping("/order")
    public OrderResponse order(@RequestBody OrderRequest orderRequest) {

        OrderResult result = stockCountInDBOrderService.order(orderRequest.toCommand());
//        OrderResult result = orderService.selectForUpdateOrder(orderRequest.toCommand());
//        OrderResult result = orderService.selectForUpdateOrder(orderRequest.toCommand());
        return OrderResponse.from(result);
    }
}
