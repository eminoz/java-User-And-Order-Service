package com.trendyol.backend.business.concretes;

import com.trendyol.backend.business.abstracts.ProductService;
import com.trendyol.backend.core.utilities.results.*;
import com.trendyol.backend.dataAccsess.abstracts.ProductDao;
import com.trendyol.backend.entities.concretes.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductManager implements ProductService {
    private ProductDao productDao;

    @Autowired
    public ProductManager(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Result add(Product product) {
        this.productDao.insert(product);
        return new ErrorResult("product added");
    }

    @Override
    public DataResult<Product> getByProductName(String productName) {
        Product productByProductName = this.productDao.findProductByProductName(productName);
        if (productByProductName == null) {
            return new ErrorDataResult<>(null, "product does not exist anymore");
        }
        SuccessDataResult<Product> productSuccessDataResult = new SuccessDataResult<>(productByProductName, "found product");
        return productSuccessDataResult;
    }

    @Override
    public DataResult<List<Product>> getAll() {
        List<Product> all = this.productDao.findAll();
        SuccessDataResult<List<Product>> all_product = new SuccessDataResult<>(all, "All product");
        return all_product;
    }
}
