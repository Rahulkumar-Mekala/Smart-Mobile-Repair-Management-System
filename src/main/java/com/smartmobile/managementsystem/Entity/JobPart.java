package com.smartmobile.managementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_parts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_job_id", nullable = false)
    private RepairJob repairJob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spare_part_id", nullable = false)
    private SparePart sparePart;

    @Column(nullable = false)
    private Integer quantityUsed;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    private LocalDateTime usedAt;

    private String remarks;

    @PrePersist
    public void onCreate() {
        usedAt = LocalDateTime.now();

        if (quantityUsed != null && unitPrice != null) {
            totalPrice = unitPrice.multiply(
                BigDecimal.valueOf(quantityUsed)
            );
        }
    }
}
