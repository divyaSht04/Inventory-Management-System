package com.individual.divya.inventorymanagementsystem.repository;

import com.individual.divya.inventorymanagementsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
