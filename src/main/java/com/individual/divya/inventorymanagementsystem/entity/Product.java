package com.individual.divya.inventorymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CsvBindByName(column="name")
    private String name;

    @CsvBindByName(column="price")
    private Double price;

    @CsvBindByName(column="description")
    private String description;

    @CsvBindByName(column="quantity")
    private int quantity;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}
