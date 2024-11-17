package com.individual.divya.inventorymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate orderDate;
    private double totalPrice;

    @ManyToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    private String status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Vendor> vendors = new ArrayList<>();
}
