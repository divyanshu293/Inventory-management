package com.divyanshu.Inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockChangeRequest {
    @NotNull
    @Min(1)
    private Long amount;


}
