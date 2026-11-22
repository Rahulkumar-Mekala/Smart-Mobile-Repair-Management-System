package com.smartmobile.managementsystem.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "spare_parts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SparePart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String partName;

    private String partCode;

    private String brand;

    private String compatibleModels;

    @Column(nullable = false)
    private Integer stockQuantity = 0;

    private Integer minimumStockLevel = 5;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    private String storageLocation;

    private Boolean active = true;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (stockQuantity == null) {
            stockQuantity = 0;
        }

        if (minimumStockLevel == null) {
            minimumStockLevel = 5;
        }
    }
}

