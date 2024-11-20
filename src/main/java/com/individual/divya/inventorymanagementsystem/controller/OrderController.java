package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.exception.NoOrdersFoundException;
import com.individual.divya.inventorymanagementsystem.exception.VendorNotFoundException;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import com.individual.divya.inventorymanagementsystem.service.UserService;
import com.individual.divya.inventorymanagementsystem.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @Autowired
    public UserService userService;

    @Autowired
    private VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (!orders.isEmpty()) {
            // update total price
            orders.forEach(order -> {
                double totalPrice = order.getProducts().stream()
                       .mapToDouble(product -> product.getPrice() * product.getQuantity())
                       .sum();

                order.setTotalPrice(totalPrice);
            });

            return new ResponseEntity<>(orders, HttpStatus.OK);
        }

        throw new NoOrdersFoundException("No orders found");
    }

    @GetMapping("/id/{orderId}")
    public ResponseEntity<?> getOrderByID(@PathVariable Long orderId) {
        List<Order> orders = orderService.getOrderById(orderId);

        List<Order> updatedOrders = orders.stream().
                peek(
                        order -> {
                            double totalPrice = order.getProducts().stream()
                                    .mapToDouble(product -> product.getPrice() * product.getQuantity())
                                    .sum();

                            order.setTotalPrice(totalPrice);
                        }
                ).toList();

        return new ResponseEntity<>(updatedOrders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        try {
            order.setStatus("pending");
            order.setOrderDate(LocalDate.now());
            orderService.saveOrder(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (Exception ex) {
            return new ResponseEntity<>("Error occurred while creating order  "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{orderID}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderID) {
        Order existingOrder = orderService.getOrderById(orderID).get(0);
        if (existingOrder!= null) {
            existingOrder.setStatus(existingOrder.getStatus().equals("pending") ? "received" : "pending");
            orderService.saveOrder(existingOrder);
            return new ResponseEntity<>(existingOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/id/{orderID}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderID) {
        try{
        Order existingOrder = orderService.getOrderById(orderID).get(0);

        if (existingOrder != null) {
            orderService.deleteOrder(existingOrder);
            return new ResponseEntity<>("Order Deleted Successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("No Order of " + orderID + " found!",HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>("Error occurred while deleting order  "+ ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/id/{orderId}/vendorId/{vendorId}")
    public ResponseEntity<?> assignVendor(@PathVariable Long orderId, @PathVariable Long vendorId) {
        Order existingOrder = orderService.getOrderById(orderId).get(0);

        Vendor vendor =  vendorService.getVendorByID(vendorId);

        if (existingOrder.getVendors().contains(vendor)) throw new VendorNotFoundException("Vendor already exists");

        existingOrder.getVendors().add(vendor);
        orderService.saveOrder(existingOrder);
        return new ResponseEntity<>(existingOrder, HttpStatus.OK);
    }
}
