package com.individual.divya.inventorymanagementsystem.repository;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
