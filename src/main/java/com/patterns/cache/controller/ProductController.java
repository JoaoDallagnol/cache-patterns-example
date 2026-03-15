package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.dto.SyncStatusResponse;
import com.patterns.cache.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @DeleteMapping("/cache")
    public ResponseEntity<Void> flushCache() {
        return ResponseEntity.noContent().build();
    }
}
