package com.individual.divya.inventorymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String contact;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "vendors")
    private List<Order> orders = new ArrayList<>();
}