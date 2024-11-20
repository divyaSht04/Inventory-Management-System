package com.individual.divya.inventorymanagementsystem.controller;

import com.individual.divya.inventorymanagementsystem.entity.Product;
import com.individual.divya.inventorymanagementsystem.service.OrderService;
import com.individual.divya.inventorymanagementsystem.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;


    @Mock
    private File mockFile;

    @Mock
    private FileSystemResource mockResource;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        when(productService.getALLProducts()).thenReturn(Collections.emptyList());

        assertNotNull(productController.getAllProducts());
    }

    @Test
    public void testCreateProduct() {
        Product product = new Product();
        product.setName("Test Product");

        doNothing().when(productService).addProduct(any(Product.class), anyLong());

        assertNotNull(productController.createProduct(product, 1L));
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(productService).DeleteProduct(anyLong(), anyLong());

        assertNotNull(productController.deleteProduct(1L, 1L));
    }

    @Test
    void testUploadProduct() {
        // Arrange
        Long orderId = 1L;
        String content = "id,name,price\n1,Test Product,99.99";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                content.getBytes()
        );

        doNothing().when(productService).parseCSV(any(), anyLong());

        // Act
        ResponseEntity<?> response = productController.uploadProduct(file, orderId);

        // Assert
        assertNotNull(response);
        assertEquals("Products uploaded successfully.", response.getBody());
        verify(productService).parseCSV(file, orderId);
    }

    @Test
    void testExportProductsAsCSV() {
        String filePath = "test/products.csv";
        when(productService.exportToCSV()).thenReturn(filePath);
        when(mockFile.length()).thenReturn(1024L);
        when(mockResource.exists()).thenReturn(true);

        ResponseEntity<Resource> response = productController.exportProductsAsCSV();

        assertNotNull(response);
        HttpHeaders headers = response.getHeaders();
        assertEquals(MediaType.parseMediaType("text/csv"), headers.getContentType());
        assertEquals("attachment; filename=\"products.csv\"",
                headers.getContentDisposition().toString());

        verify(productService).exportToCSV();
    }
}
