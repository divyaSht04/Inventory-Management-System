package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    public VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllVendors(){
        return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor){
        vendorService.addVendor(vendor);
        return new ResponseEntity<>("Vendor Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/vendorId/{vendorId}")
    public ResponseEntity<?> getVendorById(@PathVariable long vendorId){

        List<Vendor> vendor = vendorService.getVendorByID(vendorId);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }
}
