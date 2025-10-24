package com.divyanshu.Inventory.service;

import com.divyanshu.Inventory.dto.CreateProductRequest;
import com.divyanshu.Inventory.dto.ProductDTO;
import com.divyanshu.Inventory.dto.UpdateProductRequest;
import com.divyanshu.Inventory.exception.BadRequestException;
import com.divyanshu.Inventory.exception.InsufficientStockException;
import com.divyanshu.Inventory.exception.ResourceNotFoundException;
import com.divyanshu.Inventory.model.Product;
import com.divyanshu.Inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    @CachePut(cacheNames = "products",key = "#result.id")
    public ProductDTO create(CreateProductRequest req) {
        if(repo.existsByProductName(req.getName())){
            throw new BadRequestException("Product with name "+ req.getName()+" already exists");
        }

        if (req.getStockQuantity() < 0) throw new BadRequestException("stockQuantity cannot be negative");
        Product p = new Product(req.getName(), req.getDescription(), req.getStockQuantity());
        Product saved = repo.save(p);
        return toDTO(saved);
    }

    @Cacheable(cacheNames = "products")
    public List<ProductDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "products",key= "#id")
    public ProductDTO getById(Long id) {
        Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return toDTO(p);
    }

    @Transactional
    @CachePut(cacheNames = "products",key = "#id")
    public ProductDTO update(Long id, UpdateProductRequest req) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (req.getName() != null) product.setName(req.getName());
        if (req.getDescription() != null) product.setDescription(req.getDescription());
        if (req.getStockQuantity() != null) product.setStockQuantity(req.getStockQuantity());

        Product saved = repo.save(product);
        return toDTO(saved);
    }

    @CacheEvict(cacheNames = "products",key = "#id")
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Product not found with id " + id);
        repo.deleteById(id);
    }

    @Transactional
    @CachePut(cacheNames = "products",key = "#id")
    public ProductDTO increaseStock(Long id, Long amount) {
        if (amount == null || amount <= 0) throw new BadRequestException("amount must be positive");
        Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        long newQty = p.getStockQuantity() + amount;
        if (newQty < 0) throw new BadRequestException("resulting stock invalid");
        p.setStockQuantity(newQty);
        Product saved = repo.save(p);
        return toDTO(saved);
    }

    @Transactional
    public ProductDTO decreaseStock(Long id, Long amount) {
        if (amount == null || amount <= 0) throw new BadRequestException("amount must be positive");
        Product p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        if (p.getStockQuantity() < amount) {
            throw new InsufficientStockException("Insufficient stock. Available: " + p.getStockQuantity() + ", requested: " + amount);
        }
        p.setStockQuantity(p.getStockQuantity() - amount);
        Product saved = repo.save(p);
        return toDTO(saved);
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getProductName(), p.getDescription(), p.getStockQuantity());
    }

    @Cacheable(cacheNames = "products")
    public List<ProductDTO> getLowStockProducts(Long threshold) {
          return repo.stockQuantityLessThan(threshold);
    }
}
