package com.smartmobile.managementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "warranties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warranty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String warrantyNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_job_id", nullable = false)
    private RepairJob repairJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private String warrantyType;

    private Integer warrantyPeriodDays;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    private String warrantyStatus;

    private String termsAndConditions;

    private String claimDescription;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (warrantyStatus == null) {
            warrantyStatus = "ACTIVE";
        }
    }
}
