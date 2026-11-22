package com.smartmobile.managementsystem.Entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shops")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shopName;

    private String ownerName;

    private String email;

    private String phoneNumber;

    private String address;

    private Boolean active;
}