    package com.individual.divya.inventorymanagementsystem.entity;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @NoArgsConstructor
    @Table(name = "orders")
    @Getter
    @Setter
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        private LocalDate orderDate;

        private double totalPrice;

        @ManyToMany(mappedBy = "orders", fetch = FetchType.EAGER)
        @ToString.Exclude
        private List<Product> products = new ArrayList<>();

        private String status;

        @ManyToMany(fetch = FetchType.EAGER)
        private List<Vendor> vendors = new ArrayList<>();
    }
