package com.order.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`order`", schema = "order")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", nullable = false)
    private Long id;

    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    public static Order orderAccepted(Product product, Long memberId) {
        return Order.builder()
                .memberId(memberId)
                .productId(product.getId())
                .amount(product.getPrice())
                .status("ACCEPTED")
                .build();
    }

    public void orderCompleted() {
        this.status = "COMPLETED";
    }

    public void paymentFailed() {
        this.status = "PAYMENT_FAILED";
    }
}
