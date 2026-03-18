package com.patterns.cache.service;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadThroughService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        return mapper.toResponse(
            productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id))
        );
    }
}
