package com.individual.divya.inventorymanagementsystem.service;

import com.individual.divya.inventorymanagementsystem.entity.Order;
import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.exception.OrderNotFoundException;
import com.individual.divya.inventorymanagementsystem.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetALLProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getALLProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        Order order = new Order();
        order.setProducts(List.of(product));
        product.setOrders(List.of(order));

        when(orderService.getOrderById(1L)).thenReturn(List.of(order));
    }


    @Test
    void testParseCSV() throws Exception {
        Order order = new Order();
        when(orderService.getOrderById2(1L)).thenReturn(order);

        String csvContent = "ID,Name,Price,Description,Quantity\n1,Test Product,100.0,Description,10\n";
        InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());
        MultipartFile file = mock(MultipartFile.class);

        when(file.getInputStream()).thenReturn(inputStream);

        productService.parseCSV(file, 1L);

        verify(productRepository, atLeastOnce()).save(any(Product.class));
    }

    @Test
    void testExportToCSV() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product A");
        product.setPrice(10.0);
        product.setDescription("Sample Description");
        product.setQuantity(5);

        when(productRepository.findAll()).thenReturn(List.of(product));

        String filePath = productService.exportToCSV();

        assertEquals("products.csv", filePath);
        verify(productRepository, times(1)).findAll();
    }
}
