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
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_job_id", nullable = false, unique = true)
    private RepairJob repairJob;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subTotal;

    @Column(precision = 12, scale = 2)
    private BigDecimal taxAmount;

    @Column(precision = 12, scale = 2)
    private BigDecimal discountAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal paidAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal dueAmount;

    private String invoiceStatus;

    private LocalDate invoiceDate;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (invoiceDate == null) {
            invoiceDate = LocalDate.now();
        }

        if (subTotal == null) subTotal = BigDecimal.ZERO;
        if (taxAmount == null) taxAmount = BigDecimal.ZERO;
        if (discountAmount == null) discountAmount = BigDecimal.ZERO;
        if (paidAmount == null) paidAmount = BigDecimal.ZERO;
        if (totalAmount == null) totalAmount = BigDecimal.ZERO;
        if (dueAmount == null) dueAmount = BigDecimal.ZERO;

        if (invoiceStatus == null) {
            invoiceStatus = "UNPAID";
        }
    }
}
