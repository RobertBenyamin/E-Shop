package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreate() {
        // Arrange
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        // Act
        Product savedProduct = productService.create(product);

        // Assert
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAll() {
        // Arrange
        Product product1 = new Product();
        product1.setProductId("1");
        Product product2 = new Product();
        product2.setProductId("2");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2).iterator());

        // Act
        List<Product> allProducts = productService.findAll();

        // Assert
        assertEquals(2, allProducts.size());
    }
    
    @Test
    void testFindByIdWhenProductExists() {
        // Arrange
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product foundProduct = productService.findById(productId);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindByIdWhenProductNotExists() {
        // Arrange
        String productId = "1";
        String nonExistingProductId = "-1";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product foundProduct = productService.findById(nonExistingProductId);

        // Assert
        assertNull(foundProduct);
    }

    @Test
    void testGetLastId() {
        // Arrange
        String lastId1 = "0"; 
        String lastId2 = "1"; 

        // Act & Assert
        assertEquals(lastId1, productService.getLastId());
        assertEquals(lastId2, productService.getLastId());
    }

    @Test
    void testDeleteByIdWhenProductExists() {
        // Arrange
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        productService.deleteById(productId);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteByIdWhenProductNotExists() {
        // Arrange
        String productId = "1";
        String nonExistingProductId = "-1";
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> productService.deleteById(nonExistingProductId));
    }

    @Test
    void testEditWhenProductExists() {
        // Arrange
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product editedProduct = new Product();
        editedProduct.setProductId(productId);
        editedProduct.setProductName("Sampo Cap Bango");
        editedProduct.setProductQuantity(50);
        productService.edit(editedProduct);

        // Assert
        verify(productRepository, times(1)).edit(editedProduct);
    }

    @Test
    void testEditWhenProductNotExists() {
        // Arrange
        String productId = "1";
        String nonExistingProductId = "-1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        Product editedProduct = new Product();
        editedProduct.setProductId(nonExistingProductId);
        editedProduct.setProductName("Sampo Cap Bango");
        editedProduct.setProductQuantity(50);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> productService.edit(editedProduct));
    }
}
