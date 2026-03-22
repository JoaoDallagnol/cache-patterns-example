package com.patterns.cache.service;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.exception.ProductNotFoundException;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReadThroughService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        log.info("Fetching product ID {} from database (cache miss)", id);
        return mapper.toResponse(
                repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id))
        );
    }
}
