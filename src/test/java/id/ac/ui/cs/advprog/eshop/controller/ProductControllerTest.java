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
        String id = "123";
        Product product = new Product();
        product.setId(id);
        when(productService.findById(id)).thenReturn(product);
        String result = productController.deleteProductPage(id, model);
        assertEquals("deleteProduct", result);
        verify(model, times(1)).addAttribute("id", id);
        verify(model, times(1)).addAttribute("name", product.getName());
        verify(model, times(1)).addAttribute("quantity", product.getQuantity());
    }

    @Test
    void testDeleteProduct() {
        String id = "123";
        String result = productController.deleteProduct(id);
        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).deleteById(id);
    }

    @Test
    void testEditProductPage() {
        String id = "123";
        Product product = new Product();
        product.setId(id);
        when(productService.findById(id)).thenReturn(product);
        String result = productController.editProductPage(id, model);
        assertEquals("editProduct", result);
        verify(model, times(1)).addAttribute("product", product);
    }

    @Test
    void testEditProduct() {
        String id = "123";
        Product product = new Product();
        product.setId(id);
        String result = productController.editProduct(id, product);
        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).edit(product);
    }
}
