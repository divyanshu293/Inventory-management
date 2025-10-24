package com.divyanshu.Inventory.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Data
@Getter
@Setter
public class ProductDTO implements Serializable {
    private Long id;
    private String productName;
    private String description;
    private Long stockQuantity;

    public ProductDTO(Long id, String productName, String description, Long stockQuantity) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.stockQuantity = stockQuantity;
    }


}
