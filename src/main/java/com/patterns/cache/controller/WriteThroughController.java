package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.service.WriteThroughService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class WriteThroughController {
    private final WriteThroughService service;

    @PostMapping("/")
    public ResponseEntity<ProductResponse> createProductWriteThrough(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.createProductWriteThrough(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductWriteThrough(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.updateProductWriteThrough(id, request);
        return ResponseEntity.ok(response);
    }
}
