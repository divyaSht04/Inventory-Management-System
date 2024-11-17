package com.individual.divya.inventorymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Double price;
    private String description;
    private int quantity;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
}
