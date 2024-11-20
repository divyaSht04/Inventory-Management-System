package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.exception.CSVFileEmptyException;
import com.individual.divya.inventorymanagementsystem.exception.OrderNotFoundException;
import com.individual.divya.inventorymanagementsystem.exception.ProductNotFoundException;
import com.individual.divya.inventorymanagementsystem.repository.ProductRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public OrderService orderService;

    public List<Product> getALLProducts() {
        return productRepository.findAll();
    }

//    public List<Product> getProductOfUser(String email){
//        Users user = userService.findByEmail(email);
//        if(user == null){
//            throw new IllegalArgumentException("User not found with email: " + email);
//        }
//
//        return user.getOrders().stream()
//                .flatMap(order -> order.getProducts().stream())  // Access products from each order
//                .toList();
//    }

    public void addProduct(Product product, Long orderID) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Order order = orderService.getOrderById(orderID).get(0);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderID);
        }

        product.getOrders().add(order);
        productRepository.save(product);

        double totalPrice = order.getProducts().stream()
                .mapToDouble(p -> p.getPrice() * p.getQuantity())
                .sum();

        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
    }

    public void DeleteProduct(Long orderId, Long productId) {
        Order order = orderService.getOrderById(orderId).get(0);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        List<Product> product = order.getProducts()
                .stream()
                .filter(pro -> pro.getId().equals(productId))
                .toList();

        if (!product.isEmpty()) {
            order.getProducts().removeIf(pro -> pro.getId().equals(productId));
            orderService.saveOrder(order);
            productRepository.deleteById(productId);
        } else {
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }
    }

    public void parseCSV(MultipartFile file, Long orderID) {
        if (file.isEmpty()) {
            throw new CSVFileEmptyException("CSV file is empty.");
        }

        Order order = orderService.getOrderById2(orderID);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderID);
        }

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<Product> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Product.class);

            CsvToBean<Product> csvToBean = new CsvToBeanBuilder<Product>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            List<Product> products = csvToBean.parse();

            products.forEach(product -> {
                product.getOrders().add(order);
                productRepository.save(product);
            });


        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error processing CSV data: " + e.getMessage(), e);
        }
    }

    public String exportToCSV() {
        List<Product> products = productRepository.findAll();
        String filePath = "products.csv";

        try(CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {

            String[] header = {"ID", "Name", "Price", "Description" , "Quantity"};
            writer.writeNext(header);

            for(Product product : products) {
                String[] data = {String.valueOf(product.getId())
                        ,product.getName(),
                        String.valueOf(product.getPrice()),
                        product.getDescription(),
                        String.valueOf(product.getQuantity())
                };
                writer.writeNext(data);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Error :" + ex.getMessage(), ex);
        }
        return filePath;
    }

    public Product getProductById(Long orderId, Long productId) {

        Order order = orderService.getOrderById(orderId).get(0);
        if (order == null) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()){
            return product.get();
        }
        throw new ProductNotFoundException("Product not found with ID: " + productId);
    }
}
