package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setId("eb558e9f-1c39-468e-8860-71af6af63bd6");
        product1.setName("Sampo Cap Bambang");
        product1.setQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setId("a0f9de46-98b1-437d-a0bf-d0821dde9896");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getId(), savedProduct.getId());
        savedProduct = productIterator.next();
        assertEquals(product2.getId(), savedProduct.getId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());

        productRepository.delete(product);
        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFailedDelete() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Product product2 = new Product();
        product2.setId("id-salah");
        product2.setName("Sampo Cap Usep");
        product2.setQuantity(100);
        productRepository.delete(product2);

        // Check if the product is still there
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getId(), savedProduct.getId());

        Product editedProduct = new Product();
        editedProduct.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setName("Sampo Cap Bambang");
        editedProduct.setQuantity(50);
        productRepository.edit(editedProduct);

        productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        savedProduct = productIterator.next();

        assertEquals(editedProduct.getId(), savedProduct.getId());
        assertEquals(editedProduct.getName(), savedProduct.getName());
        assertEquals(editedProduct.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testFailedEdit() {
        Product product = new Product();
        product.setId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setName("Sampo Cap Bambang");
        product.setQuantity(100);
        productRepository.create(product);

        Product editedProduct = new Product();
        editedProduct.setId("id-salah");
        editedProduct.setName("Sampo Cap Bambang");
        editedProduct.setQuantity(50);
        productRepository.edit(editedProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        // Check if the product is still the same
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getQuantity(), savedProduct.getQuantity());
    }
}
