package com.divyanshu.Inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateProductRequest {
    private String name;
    private String description;

    @NotNull
    @Min(0)
    private Long stockQuantity;

}
