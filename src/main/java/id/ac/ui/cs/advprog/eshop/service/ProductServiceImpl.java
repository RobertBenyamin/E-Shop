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
    private int lastId;

    @Override
    public Product create(Product product) {
        product.setId(getLastId());
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
    public Product findById(String id) {
        Iterator<Product> productIterator = productRepository.findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public String getLastId() {
        if (lastId == 0) {
            lastId++;
            return "0";
        }
        return String.format("%d", lastId++);
    }

    @Override
    public void deleteById(String id) {
        Product product = findById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.delete(product);
    }

    @Override
    public Product edit(Product product) {
        Product editedProduct = findById(product.getId());
        if (editedProduct == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.edit(product);
        return product;
    }
}
