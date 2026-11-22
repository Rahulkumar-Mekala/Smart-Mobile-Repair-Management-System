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
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String expenseTitle;

    @Column(nullable = false)
    private String expenseCategory;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    private String paymentMethod;

    private LocalDate expenseDate;

    private String receiptNumber;

    private String description;

    private String expenseStatus;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by")
    private User recordedBy;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (expenseDate == null) {
            expenseDate = LocalDate.now();
        }

        if (expenseStatus == null) {
            expenseStatus = "PAID";
        }
    }
}

