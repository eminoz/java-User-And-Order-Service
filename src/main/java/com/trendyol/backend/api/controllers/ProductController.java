package com.trendyol.backend.api.controllers;

import com.trendyol.backend.business.abstracts.ProductService;
import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/createProduct")
    public Result createProduct(@RequestBody Product product) {
        return this.productService.add(product);
    }

    @GetMapping("findProductByProductName/{productName}")
    public DataResult<Product> findProductByProductName(@PathVariable("productName") String productName) {
        DataResult<Product> byProductName = this.productService.getByProductName(productName);
        return byProductName;
    }

    @GetMapping("/getAll")
    public DataResult<List<Product>> getAll() {
        return this.productService.getAll();
    }


}
