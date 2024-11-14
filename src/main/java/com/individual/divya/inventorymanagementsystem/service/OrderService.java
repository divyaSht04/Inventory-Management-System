package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.repository.OrderRepository;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public UserRepository userRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByEmail(String email){
        Users user = userRepository.findByEmail(email);
        if(user == null){
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return user.getOrders();
    }
}
