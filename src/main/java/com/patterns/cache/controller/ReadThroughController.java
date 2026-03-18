package com.patterns.cache.controller;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.service.ReadThroughService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ReadThroughController {

    private final ReadThroughService service;

    @GetMapping("/{id}/read-through")
    public ResponseEntity<ProductResponse> getProductReadThrough(@PathVariable Long id) {
        ProductResponse response = service.getProductById(id);
        return ResponseEntity.ok(response);
    }
}
