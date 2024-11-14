package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.exception.EmailIDNoMatchException;
import com.individual.divya.inventorymanagementsystem.exception.NoOrdersFoundException;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        List<Order> orders = orderService.getAllOrders();
        System.out.println("I am here!");
        if(!orders.isEmpty()){
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
        System.out.println("I am here too");
        throw new NoOrdersFoundException("No orders found");
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getOrderByEmail(@PathVariable String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        System.out.println(email);

        if(!email.equals(currentUserEmail)){
            throw new EmailIDNoMatchException("Please Log into from your account! ");
        }

        List<Order> orders = orderService.getOrdersByEmail(email);
        return new ResponseEntity<>(orders, HttpStatus.NOT_IMPLEMENTED);
    }
}
