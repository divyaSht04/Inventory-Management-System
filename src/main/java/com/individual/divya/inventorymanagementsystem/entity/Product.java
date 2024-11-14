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
    private Long id;
    private String name;
    private Double price;
    private String description;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
}
