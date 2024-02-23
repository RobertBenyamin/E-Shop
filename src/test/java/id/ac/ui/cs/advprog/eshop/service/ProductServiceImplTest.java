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
        product.setId("1");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        when(productRepository.create(any(Product.class))).thenReturn(product);

        // Act
        Product savedProduct = productService.create(product);

        // Assert
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAll() {
        // Arrange
        Product product1 = new Product();
        product1.setId("1");
        Product product2 = new Product();
        product2.setId("2");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2).iterator());

        // Act
        List<Product> allProducts = productService.findAll();

        // Assert
        assertEquals(2, allProducts.size());
    }

    @Test
    void testFindByIdWhenProductExists() {
        // Arrange
        String id = "1";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product foundProduct = productService.findById(id);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    void testFindByIdWhenProductNotExists() {
        // Arrange
        String id = "1";
        String nonExistingId = "-1";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product foundProduct = productService.findById(nonExistingId);

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
        String id = "1";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        productService.deleteById(id);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteByIdWhenProductNotExists() {
        // Arrange
        String id = "1";
        String nonExistingId = "-1";
        Product product = new Product();
        product.setId(id);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> productService.deleteById(nonExistingId));
    }

    @Test
    void testEditWhenProductExists() {
        // Arrange
        String id = "1";
        Product product = new Product();
        product.setId(id);
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act
        Product editedProduct = new Product();
        editedProduct.setId(id);
        editedProduct.setName("Sampo Cap Bango");
        editedProduct.setQuantity(50);
        productService.edit(editedProduct);

        // Assert
        verify(productRepository, times(1)).edit(editedProduct);
    }

    @Test
    void testEditWhenProductNotExists() {
        // Arrange
        String id = "1";
        String nonExistingId = "-1";
        Product product = new Product();
        product.setId(id);
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        Product editedProduct = new Product();
        editedProduct.setId(nonExistingId);
        editedProduct.setName("Sampo Cap Bango");
        editedProduct.setQuantity(50);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product).iterator());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> productService.edit(editedProduct));
    }
}
