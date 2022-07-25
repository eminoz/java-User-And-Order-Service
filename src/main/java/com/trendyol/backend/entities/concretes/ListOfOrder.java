package com.trendyol.backend.entities.concretes;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListOfOrder {
    private String productId;
    private int quantity;
}
