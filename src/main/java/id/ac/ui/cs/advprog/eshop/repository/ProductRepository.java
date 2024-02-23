package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void delete(Product product) {
        productData.remove(product);
    }

    public Product edit(Product product) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getId().equals(product.getId())) {
                productData.set(i, product);
                return product;
            }
        }
        return null;
    }
}
