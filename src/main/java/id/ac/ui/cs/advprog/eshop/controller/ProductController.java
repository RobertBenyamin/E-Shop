package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProductPage(@PathVariable String productId, Model model) {
        Product product = service.findById(productId);
        model.addAttribute("productId", product.getProductId());
        model.addAttribute("productName", product.getProductName());
        model.addAttribute("productQuantity", product.getProductQuantity());
        return "deleteProduct";
    }

    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId) {
        service.deleteById(productId);
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, Model model) {
        Product product = service.findById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{productId}")
    public String editProduct(@PathVariable String productId, @ModelAttribute Product product) {
        service.edit(product);
        return "redirect:/product/list";
    }
}
