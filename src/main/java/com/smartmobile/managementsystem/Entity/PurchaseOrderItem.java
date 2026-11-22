package com.smartmobile.managementsystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @Column(nullable = false)
    private Integer orderedQuantity;

    private Integer receivedQuantity = 0;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    private String itemStatus;

    @PrePersist
    public void onCreate() {
        if (receivedQuantity == null) {
            receivedQuantity = 0;
        }

        if (itemStatus == null) {
            itemStatus = "PENDING";
        }

        if (orderedQuantity != null && unitPrice != null) {
            totalPrice = unitPrice.multiply(
                BigDecimal.valueOf(orderedQuantity)
            );
        }
    }
}
