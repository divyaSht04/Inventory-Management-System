package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.entity.Vendor;
import com.individual.divya.inventorymanagementsystem.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testSaveOrder() {
        Order order = new Order();
        orderService.saveOrder(order);

        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder() {

        Order existingOrder = new Order();

        orderService.deleteOrder(existingOrder);

        verify(orderRepository, times(1)).delete(existingOrder);
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        List<Order> result = orderService.getOrderById(1L);

        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepository, times(1)).findById(1L);
    }

}