package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import com.individual.divya.inventorymanagementsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('Admin')")
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.getALLProducts(), HttpStatus.OK);
    }

    @PostMapping("/orderId/{orderId}")
    public ResponseEntity<?> createProduct(@RequestBody Product product, @PathVariable Long orderId) {
        try {
            productService.addProduct(product, orderId);
            return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("orderId/{orderId}/productId/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long orderId, @PathVariable Long productId) {
        try {
            productService.DeleteProduct(orderId, productId);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
