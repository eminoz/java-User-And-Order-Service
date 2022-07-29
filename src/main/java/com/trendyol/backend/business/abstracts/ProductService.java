package com.trendyol.backend.business.abstracts;

import com.trendyol.backend.core.utilities.results.DataResult;
import com.trendyol.backend.core.utilities.results.Result;
import com.trendyol.backend.entities.concretes.Product;

import java.util.List;

public interface ProductService {
    Result add(Product product);

    DataResult<Product> getByProductName(String productName);

    DataResult<List<Product>> getAll();
}
