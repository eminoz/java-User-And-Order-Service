package com.trendyol.backend.entities.concretes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    private String id;
    private String productName;
    private Float price;
    private String description;
    private int unitsInStock;
}
