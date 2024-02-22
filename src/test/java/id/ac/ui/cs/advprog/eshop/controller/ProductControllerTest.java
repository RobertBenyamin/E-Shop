package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.ui.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductPage() {
        String result = productController.createProductPage(model);
        assertEquals("createProduct", result);
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String result = productController.createProductPost(product, model);
        assertEquals("redirect:list", result);
        verify(productService, times(1)).create(product);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        when(productService.findAll()).thenReturn(products);
        String result = productController.productListPage(model);
        assertEquals("productList", result);
        verify(model, times(1)).addAttribute("products", products);
    }

    @Test
    void testDeleteProductPage() {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);
        String result = productController.deleteProductPage(productId, model);
        assertEquals("deleteProduct", result);
        verify(model, times(1)).addAttribute("productId", productId);
        verify(model, times(1)).addAttribute("productName", product.getProductName());
        verify(model, times(1)).addAttribute("productQuantity", product.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        String productId = "123";
        String result = productController.deleteProduct(productId);
        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).deleteById(productId);
    }

    @Test
    void testEditProductPage() {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);
        when(productService.findById(productId)).thenReturn(product);
        String result = productController.editProductPage(productId, model);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProduct() {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);
        String result = productController.editProduct(productId, product);
        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).edit(product);
    }
}
