package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        product.setProductId(getLastId());
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
    
    @Override
    public Product findById(String productId) {
        Iterator<Product> productIterator = productRepository.findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String getLastId() {
        Iterator<Product> productIterator = productRepository.findAll();
        String lastId = "0";
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            String productId = product.getProductId();
            if (productId.compareTo(lastId) > 0) {
                lastId = productId;
            }
        }
        int lastIdNumber = Integer.parseInt(lastId);
        lastIdNumber++;
        lastId = String.format("%d", lastIdNumber);
        return lastId;
    }

    @Override
    public void deleteById(String productId) {
        Product product = findById(productId);
        if(product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.delete(product);
    }
}