package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.service.CacheAsideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class CacheAsideController {

    private final CacheAsideService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductCacheAside(@PathVariable Long id) {
        ProductResponse response = service.getProductCacheAside(id);
        return ResponseEntity.ok(response);
    }
}
