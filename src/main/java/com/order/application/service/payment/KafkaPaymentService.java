package com.order.application.service.payment;

import com.order.application.model.payment.PaymentCommand;
import com.order.application.model.payment.PaymentResult;
import com.order.application.repository.order.OrderRepository;
import com.order.application.repository.product.ProductRepository;
import com.order.domain.Order;
import com.order.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPaymentService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public void pay(Long orderId) {
        kafkaTemplate.send("payment-topic", String.valueOf(orderId));
        log.info("send: {}", orderId);
    }

    @KafkaListener(topics = "payment-topic", groupId = "consumer-group")
    public void completeOrder(String orderId) {
        log.info("Listened: {}", orderId);
        Order order =
                orderRepository
                        .findById(Long.valueOf(orderId))
                        .orElseThrow(() -> new RuntimeException("없는 주문"));

        // 결제
        PaymentResult paymentResult =
                paymentService.pay(PaymentCommand.of(order.getAmount(), order.getMemberId()));
        if (Boolean.TRUE.equals(paymentResult.isSuccess())) {
            order.orderCompleted();
        } else {
            Product product =
                    productRepository
                            .findByIdForUpdate(order.getProductId())
                            .orElseThrow(() -> new RuntimeException("유효하지 않은 상품"));
            product.sellingCancelled();
            productRepository.save(product);

            order.paymentFailed();
        }
        Order savedOrder = orderRepository.save(order);
        log.info("Order ID: {}", savedOrder.getId());
    }
}
