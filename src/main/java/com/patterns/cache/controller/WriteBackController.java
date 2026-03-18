package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.service.WriteBackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class WriteBackController {

    private final WriteBackService service;

    @PutMapping("/{id}/write-back")
    public ResponseEntity<ProductResponse> updateProductWriteBack(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.updateProductWriteBack(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/{id}/write-back-queue")
    public ResponseEntity<ProductResponse> updateProductWriteBehindQueue(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.updateProductWriteBackQueue(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
