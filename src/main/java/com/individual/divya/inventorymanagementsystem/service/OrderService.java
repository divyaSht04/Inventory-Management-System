package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.entity.Users;
import com.individual.divya.inventorymanagementsystem.exception.OrderNotFoundException;
import com.individual.divya.inventorymanagementsystem.repository.OrderRepository;
import com.individual.divya.inventorymanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    public OrderRepository orderRepository;


    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public void saveOrder(Order order){
        orderRepository.save(order);
    }

    public List<Order> getOrderById(long id){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
           return List.of(order.get());
        }
        throw new OrderNotFoundException("Order not found with ID: " + id);
    }

    public void deleteOrder(Order existingOrder) {
        existingOrder.getProducts().clear();
        existingOrder.getVendors().clear();
        orderRepository.delete(existingOrder);
    }
}
