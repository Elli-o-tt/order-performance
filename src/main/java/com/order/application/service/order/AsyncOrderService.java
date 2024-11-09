package com.order.application.service.order;

import com.order.application.model.order.OrderCommand;
import com.order.application.model.order.OrderResult;
import com.order.application.repository.order.OrderRepository;
import com.order.application.repository.product.ProductRepository;
import com.order.application.service.payment.KafkaPaymentService;
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
public class AsyncOrderService {

    private final KafkaPaymentService kafkaPaymentService;
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
        Product product =
                productRepository
                        .findByIdForUpdate(orderCommand.productId())
                        .orElseThrow(() -> new RuntimeException("유효하지 않은 상품"));

        log.info(String.valueOf(product.getStockCount()));
        if (product.getStockCount() <= 0) {
            throw new RuntimeException("재고 없는 상품");
        }

        // 주문 처리
        product.sold();
        productRepository.save(product);
        Order order = Order.orderAccepted(product, memberId);
        Order savedOrder = orderRepository.save(order);

        kafkaPaymentService.pay(savedOrder.getId());
        log.info("End:" + LocalDateTime.now().format(formatter));
        log.info("====================================================");
        log.info("");

        return OrderResult.of(savedOrder.getId());
    }
}
