package com.divyanshu.Inventory.controllers;

import com.divyanshu.Inventory.dto.*;
import com.divyanshu.Inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService svc;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest req) {
        ProductDTO created = svc.create(req);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }
    @RequestMapping("/")
    public String greet(){
        return "Hello World";
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(svc.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(svc.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest req) {
        return ResponseEntity.ok(svc.update(id, req));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        svc.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/increase")
    public ResponseEntity<ProductDTO> increaseStock(@PathVariable Long id, @Valid @RequestBody StockChangeRequest req) {
        return ResponseEntity.ok(svc.increaseStock(id, req.getAmount()));
    }


    @PostMapping("/{id}/decrease")
    public ResponseEntity<ProductDTO> decreaseStock(@PathVariable Long id, @Valid @RequestBody StockChangeRequest req) {
        return ResponseEntity.ok(svc.decreaseStock(id, req.getAmount()));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(@RequestParam("threshold") Long threshold){
        return ResponseEntity.ok(svc.getLowStockProducts(threshold));
    }
}
