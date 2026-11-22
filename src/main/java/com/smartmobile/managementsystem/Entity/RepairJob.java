package com.smartmobile.managementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String jobNumber;

    @Column(nullable = false)
    private String problemDescription;

    private String diagnosis;

    private String repairStatus;

    private String priority;

    private BigDecimal estimatedCost;

    private BigDecimal finalCost;

    private LocalDateTime receivedAt;

    private LocalDateTime estimatedDeliveryDate;

    private LocalDateTime deliveredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @PrePersist
    public void onCreate() {
        receivedAt = LocalDateTime.now();

        if (repairStatus == null) {
            repairStatus = "RECEIVED";
        }

        if (priority == null) {
            priority = "NORMAL";
        }
    }
}
