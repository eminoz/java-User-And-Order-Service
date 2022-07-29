package com.trendyol.backend.dataAccsess.abstracts;

import com.trendyol.backend.entities.concretes.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends MongoRepository<Product, String> {
    Product findProductByProductName(String productName);
}
