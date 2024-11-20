package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.exception.ProductNotFoundException;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import com.individual.divya.inventorymanagementsystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


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


    @GetMapping("/orderId/{orderId}/productId/{productId}")
    public ResponseEntity<?> getProductByID(@PathVariable Long orderId, @PathVariable Long productId) {
        try {
            Product product = productService.getProductById(orderId, productId);
            if (product == null) {
                throw new ProductNotFoundException("Product with "+ productId + " not found");
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
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

    @PostMapping(value = "/orderId/{orderId}/csv", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadProduct(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long orderId) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty. Please upload a valid CSV file.", HttpStatus.BAD_REQUEST);
        }
        try {
            productService.parseCSV(file, orderId);
            return new ResponseEntity<>("Products uploaded successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading products: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportProductsAsCSV() {
        try {
            String filePath = productService.exportToCSV();
            File file = new File(filePath);
            Resource resource = new FileSystemResource(file);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/csv"));
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename("products.csv")
                    .build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
