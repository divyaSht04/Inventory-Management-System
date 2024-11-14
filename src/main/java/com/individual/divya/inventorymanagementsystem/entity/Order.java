package com.individual.divya.inventorymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String orderDate;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToMany(mappedBy = "orders")
    private List<Product> products = new ArrayList<>();

}
