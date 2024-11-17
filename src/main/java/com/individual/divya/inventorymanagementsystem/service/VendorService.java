package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.exception.UniqueConstraintViolationException;
import com.individual.divya.inventorymanagementsystem.exception.VendorNotFoundException;
import com.individual.divya.inventorymanagementsystem.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    public VendorRepository vendorRepository;

    public List<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    public void addVendor(Vendor vendor){
        try{
            vendorRepository.save(vendor);
        }catch (DataIntegrityViolationException e){
            throw new UniqueConstraintViolationException("Vendor already exists");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vendor> getVendorByID(Long vendorID){
        Optional<Vendor> vendor = vendorRepository.findById(vendorID);

        if (vendor.isPresent()){
            return List.of(vendor.get());
        }

        throw new VendorNotFoundException("Vendor with id: " + vendor + " not found");
    }
}
