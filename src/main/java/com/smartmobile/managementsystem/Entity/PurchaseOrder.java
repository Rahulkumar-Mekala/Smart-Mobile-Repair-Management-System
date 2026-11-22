package com.smartmobile.managementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String purchaseOrderNumber;

    private LocalDate orderDate;

    private LocalDate expectedDeliveryDate;

    private LocalDate receivedDate;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    private String orderStatus;

    private String paymentStatus;

    private String remarks;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (orderDate == null) {
            orderDate = LocalDate.now();
        }

        if (orderStatus == null) {
            orderStatus = "DRAFT";
        }

        if (paymentStatus == null) {
            paymentStatus = "PENDING";
        }

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }
    }
}
