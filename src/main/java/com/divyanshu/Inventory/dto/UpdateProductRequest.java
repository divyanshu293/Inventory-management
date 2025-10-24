package com.divyanshu.Inventory.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProductRequest {
    private String productName;
    private String description;

    @Min(0)
    private Long stockQuantity;

    public String getName() {
        return productName;
    }
}
