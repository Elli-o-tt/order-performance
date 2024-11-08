package com.order.application.service.order;

import static java.lang.Boolean.TRUE;

import com.order.application.model.order.OrderCommand;
import com.order.application.model.order.OrderResult;
import com.order.application.model.payment.PaymentCommand;
import com.order.application.repository.Order.OrderRepository;
import com.order.application.repository.product.ProductRepository;
import com.order.application.service.payment.PaymentService;
import com.order.domain.Order;
import com.order.domain.Product;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockCountInDBOrderService {

    private final PaymentService paymentService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Transactional
    public OrderResult order(OrderCommand orderCommand) {
        orderCommand.validate();
        log.info("====================================================");
        log.info("Start:" + LocalDateTime.now().format(formatter));
        Long memberId = orderCommand.memberId();
        Product product = productRepository.findByIdForUpdate(orderCommand.productId()).orElse(null);

        if (product == null) {
            throw new RuntimeException("유효하지 않은 상품");
        }


        if (product.getStockCount() <= 0) {
            throw new RuntimeException("재고 없는 상품");
        }

        // 결제
        paymentService.pay(PaymentCommand.of(product, memberId, orderCommand.paymentInfo()));

        // 재고 감소
        product.sold();
        productRepository.save(product);

        log.info(String.valueOf(product.getStockCount()));
        // 주문 처리
        Order order = Order.of(product, memberId);
        Order savedOrder = orderRepository.save(order);
        log.info("End:" + LocalDateTime.now().format(formatter));
        log.info("====================================================");
        log.info("");

        return OrderResult.builder().isSuccess(TRUE).orderId(savedOrder.getId()).build();
    }
}