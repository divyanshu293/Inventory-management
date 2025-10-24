package com.divyanshu.Inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;


    private Long stockQuantity;
    @Column(length = 1000)

    private String description;

    private Long version;



    public Product(){}

    public Product(@NotBlank String name, String description, @NotNull @Min(0) Long stockQuantity) {
          productName  = name;
          this.description = description;
          this.stockQuantity = stockQuantity;


    }

    public void setName(String name) {
        productName = name;
    }
}
