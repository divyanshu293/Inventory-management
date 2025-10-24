package com.divyanshu.Inventory.repository;

import com.divyanshu.Inventory.dto.ProductDTO;
import com.divyanshu.Inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    boolean existsByProductName(String name);

    List<ProductDTO> stockQuantityLessThan(Long threshold);
}
