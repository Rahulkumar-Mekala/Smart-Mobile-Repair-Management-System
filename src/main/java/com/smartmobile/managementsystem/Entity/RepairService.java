package com.smartmobile.managementsystem.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepairService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal standardPrice;

    private Integer estimatedTimeInMinutes;

    private Boolean active = true;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
