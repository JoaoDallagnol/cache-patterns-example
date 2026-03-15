package com.patterns.cache.service;

import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheAsideProductService {

    private final ProductRepository productRepository;

    public ProductResponse getById(Long id) {
        // try get from cache
        // if not exists in cache
        // get from database
        // save to cache
        // return result
        return null;
    }

    public List<ProductResponse> getAll() {
        return null;
    }
}
