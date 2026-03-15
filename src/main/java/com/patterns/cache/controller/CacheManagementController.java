package com.patterns.cache.controller;

import com.patterns.cache.service.CacheManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CacheManagementController {

    private final CacheManagementService service;

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/cache")
    public ResponseEntity<Void> flushCache() {
        return ResponseEntity.noContent().build();
    }
}
