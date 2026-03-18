package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.service.CacheAsideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class CacheAsideController {

    private final CacheAsideService service;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductCacheAside(@PathVariable Long id) {
        ProductResponse response = service.getProductCacheAside(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductResponse>> getAllProductsCacheAside() {
        List<ProductResponse> response = service.getAllProductsCacheAside();
        return ResponseEntity.ok(response);
    }
}
