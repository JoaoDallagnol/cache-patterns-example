package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteThroughService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductResponse create(ProductRequest request) {
        return null;
    }

    public ProductResponse update(Long id, ProductRequest request) {
        return null;
    }

}
