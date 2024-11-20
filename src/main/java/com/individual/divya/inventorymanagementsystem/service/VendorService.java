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

    public Vendor getVendorByID(Long vendorID){
        return vendorRepository.findById(vendorID)
                .orElseThrow(() -> new VendorNotFoundException("Vendor with id: " + vendorID + " not found"));
    }

    public void deleteVendor(long vendorId) {
        try{
            Optional<Vendor> vendor = vendorRepository.findById(vendorId);
            if(vendor.isPresent()){

                // Remove orders associated with the vendor
                vendor.get().getOrders().forEach(order -> order.getVendors()
                        .removeIf(v->v.getId() == vendorId));

                vendorRepository.deleteById(vendorId);
            }else{
                throw new VendorNotFoundException("Vendor not found with ID: " + vendorId);
            }
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
