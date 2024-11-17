package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.exception.OrderNotFoundException;
import com.individual.divya.inventorymanagementsystem.exception.ProductNotFoundException;
import com.individual.divya.inventorymanagementsystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public UserService userService;

    @Autowired
    public OrderService orderService;

    public List<Product> getALLProducts(){
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
        if(product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        Order order =  orderService.getOrderById(orderID).get(0);
        if(order == null){
            throw new IllegalArgumentException("Order not found with ID: " + orderID);
        }

        product.getOrders().add(order);
        productRepository.save(product);
        orderService.saveOrder(order);
    }

    public void DeleteProduct(Long orderId, Long productId) {
        Order order = orderService.getOrderById(orderId).get(0);
        if(order == null){
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
        }else{
            throw new ProductNotFoundException("Product not found with ID: " + productId);
        }

    }
}
