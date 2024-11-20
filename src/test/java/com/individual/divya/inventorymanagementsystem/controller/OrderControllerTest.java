package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import com.individual.divya.inventorymanagementsystem.service.UserService;
import com.individual.divya.inventorymanagementsystem.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;


    @Mock
    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        Product product = new Product();
        product.setPrice(10.0);
        product.setQuantity(2);
        List<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.getAllOrders()).thenReturn(orders);

        assertNotNull(orderController.getAllOrders());
    }

    @Test
    public void testGetOrderByID() {
        Order order = new Order();
        Product product = new Product();
        product.setPrice(15.0);
        product.setQuantity(3);
        List<Product> products = new ArrayList<>();
        products.add(product);
        order.setProducts(products);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.getOrderById(1L)).thenReturn(orders);

        assertNotNull(orderController.getOrderByID(1L));
    }

    @Test
    public void testAddOrder() {
        // Mock data
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setStatus("pending");

        doNothing().when(orderService).saveOrder(any(Order.class));
        assertNotNull(orderController.addOrder(order));
    }

    @Test
    public void testUpdateOrder() {
        Order order = new Order();
        order.setStatus("pending");

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.getOrderById(1L)).thenReturn(orders);
        doNothing().when(orderService).saveOrder(any(Order.class));

        assertNotNull(orderController.updateOrder(1L));
    }

    @Test
    public void testDeleteOrder() {
        Order order = new Order();
        List<Order> orders = new ArrayList<>();
        orders.add(order);


        when(orderService.getOrderById(1L)).thenReturn(orders);

        assertNotNull(orderController.deleteOrder(1L));
    }

    @Test
    public void testAssignVendor() {

        Order order = new Order();
        Vendor vendor = new Vendor();
        vendor.setId(1L);

        List<Vendor> vendors = new ArrayList<>();
        order.setVendors(vendors);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderService.getOrderById(1L)).thenReturn(orders);
        when(vendorService.getVendorByID(1L)).thenReturn(vendor);
        doNothing().when(orderService).saveOrder(any(Order.class));

        assertNotNull(orderController.assignVendor(1L, 1L));
    }
}
