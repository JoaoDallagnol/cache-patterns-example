package com.patterns.cache.service;

import com.patterns.cache.dto.ProductRequest;
import com.patterns.cache.dto.ProductResponse;
import com.patterns.cache.mapper.ProductMapper;
import com.patterns.cache.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriteBehindService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductResponse update(Long id, ProductRequest request) {
        return null;
    }

    public void getProductSyncStatus(Long id) {
    }
}
